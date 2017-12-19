package com.polytech.planning.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.polytech.planning.model.Calendar;
import com.polytech.planning.model.Course;
import com.polytech.planning.model.Planning;
import com.polytech.planning.model.Semester;
import com.polytech.planning.model.Teacher;
import com.polytech.planning.model.TeachingUnit;

public class WritePlanning extends WriteFile {

	private List<Planning> plannings;
	private Workbook workbook;
	private String year;
	private HashMap<String, Sheet> sheets;
	private int lastWritenRow; // derniere ligne ecrite
	private int[] lastTURow;// 0=> last row of teaching unit in sheet1, 1=> ...
	private nameYear nameYear;

	private enum nameYear {
		DI3, DI4, DI5;
	}

	/**
	 * Constructor
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public WritePlanning(List<Planning> plannings, String year, String filePath) throws Exception {
		super(filePath);
		this.lastWritenRow = 0;
		if (plannings == null || plannings.isEmpty())
			throw new Exception("Error: Planning is empty!");
		this.plannings = plannings;
		this.year = year;
		this.workbook = super.getWorkbook();
		this.sheets = new HashMap<String, Sheet>();
		this.lastTURow = new int[2];

		this.nameYear = nameYear.valueOf(year); // surround with try/catch
	}

	/**
	 * Create Planning in to excel file.
	 * 
	 * @param planning
	 */
	public void createFile() {
		// System.out.println(planning.getTeachingUnits().isEmpty());
		int i = 0;
		for (Planning planning : this.plannings) {
			Sheet sheet = this.workbook.createSheet("Planning " + planning.getCalendar().getName());
			sheets.put(planning.getCalendar().getName(), sheet);

			this.writeIntroPart(planning);
			this.lastTURow[i] = this.writeTeachingUnits(planning);
			this.writeWeeks(planning, i);
			i++;
		}
		System.out.println(this.lastTURow[0] + "," + this.lastTURow[1]);
		FileOutputStream output;
		try {
			output = new FileOutputStream(super.getFilePath());
			this.workbook.write(output);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int writeTeachingUnits(Planning planning) {
		List<TeachingUnit> teachingUnits = planning.getTeachingUnits();
		ToolBox.checkCourseType(teachingUnits);
		int courseStartRow = lastWritenRow, teachingUnitStartRow = lastWritenRow;
		int lastRow;

		for (TeachingUnit teachingUnit : teachingUnits) {

			lastRow = this.writeCourses(courseStartRow, sheets.get(planning.getCalendar().getName()), teachingUnit) - 1;
			// Row row = sheet.createRow(courseEndRow);
			if (teachingUnitStartRow < lastRow)
				StylesLib.setCellMerge(sheets.get(planning.getCalendar().getName()), teachingUnitStartRow, lastRow, 0,
						0);
			this.writeTeachingUnit(teachingUnitStartRow, sheets.get(planning.getCalendar().getName()),
					teachingUnit.getName());
			teachingUnitStartRow = lastRow + 1;
			courseStartRow = teachingUnitStartRow;
		}

		return teachingUnitStartRow - 1;
	}

	public int writeCourses(int courseStartRow, Sheet sheet, TeachingUnit teachingUnit) {
		int courseEndRow = courseStartRow;
		int nowRow, lastRow = courseStartRow;
		for (Course course : teachingUnit.getListCourses()) {
			for (Teacher t : course.getListTeachers()) {
				System.out.println(t.getName());
			}

			nowRow = this.writeTeachers(courseStartRow, sheet, course);

			courseEndRow = nowRow - 1;
			if (courseStartRow < courseEndRow) {
				StylesLib.setCellMerge(sheet, courseStartRow, courseEndRow, 1, 1);
			}

			this.writeCourse(courseStartRow, sheet, course.getName());
			courseStartRow = courseEndRow + 1;

			// test mundus
			if (course.isMundus()) {
				nowRow = this.writeMundusTeachers(courseStartRow, sheet, course);
				courseEndRow = nowRow - 1;
				if (courseStartRow < courseEndRow) {
					StylesLib.setCellMerge(sheet, courseStartRow, courseEndRow, 1, 1);
				}

				this.writeCourse(courseStartRow, sheet, course.getName() + "_mundus");
				courseStartRow = courseEndRow + 1;
			}

			lastRow = nowRow;
		}
		return lastRow;
	}

	private int writeMundusTeachers(int teacherStartRow, Sheet sheet, Course course) {
		int teacherEndRow = teacherStartRow;
		int lastRow = 0;
		for (Teacher teacher : course.getListTeachers()) {

			if (teacher.getTDMundus() != 0) {
				// Row row = sheet.createRow(teacherEndRow);
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "TD");
				cell.setCellStyle(StylesLib.tdStyle((XSSFWorkbook) workbook));

				this.writeTeacher(teacherEndRow, sheet, teacher.getName());
				this.writeHoursPut(teacherEndRow, sheet, teacher.getTDMundus());
				if (this.year.equalsIgnoreCase("DI3"))
					this.writeBooleanDI3(teacherEndRow, sheet, true);
				teacherEndRow++;
				lastRow = teacherEndRow;

			}
			if (teacher.getTPMundus() != 0) {

				// Row row = sheet.createRow(teacherEndRow);
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "TP");
				cell.setCellStyle(StylesLib.tpStyle((XSSFWorkbook) workbook));

				this.writeTeacher(teacherEndRow, sheet, teacher.getName());
				this.writeHoursPut(teacherEndRow, sheet, teacher.getTPMundus());
				if (this.year.equalsIgnoreCase("DI3"))
					this.writeBooleanDI3(teacherEndRow, sheet, true);
				teacherEndRow++;
				lastRow = teacherEndRow;

			}

			if (teacherStartRow < teacherEndRow - 1) {
				StylesLib.setCellMerge(sheet, teacherStartRow, teacherEndRow - 1, 2, 2);
				// StylesLib.setCellMerge(sheet, teacherStartRow, teacherEndRow
				// - 1, 3, 3);
				// StylesLib.setCellMerge(sheet, teacherStartRow, teacherEndRow
				// - 1, 4, 4);
			}

			teacherStartRow = teacherEndRow;
		}
		return lastRow;
	}

	private int writeTeachers(int teacherStartRow, Sheet sheet, Course course) {
		int teacherEndRow = teacherStartRow;
		int lastRow = 0;
		for (Teacher teacher : course.getListTeachers()) {

			if (teacher.getHoursCM() != 0) {
				// Row row = sheet.createRow(teacherEndRow);
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "CM");
				cell.setCellStyle(StylesLib.cmStyle((XSSFWorkbook) workbook));
				this.writeHoursPut(teacherEndRow, sheet, teacher.getHoursCM());
				this.writeTeacher(teacherEndRow, sheet, teacher.getName());
				if (this.year.equalsIgnoreCase("DI3"))
					this.writeBooleanDI3(teacherEndRow, sheet, false);
				if (this.year.equalsIgnoreCase("DI4") || this.year.equalsIgnoreCase("DI5"))
					this.writeBooleanDI4(teacherEndRow, sheet, course.getType());
				teacherEndRow++;
				lastRow = teacherEndRow;

			}

			if (teacher.getHoursTD() != 0) {

				// Row row = sheet.createRow(teacherEndRow);
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "TD");
				cell.setCellStyle(StylesLib.tdStyle((XSSFWorkbook) workbook));
				this.writeHoursPut(teacherEndRow, sheet, teacher.getHoursTD());
				this.writeTeacher(teacherEndRow, sheet, teacher.getName());
				if (this.year.equalsIgnoreCase("DI3"))
					this.writeBooleanDI3(teacherEndRow, sheet, false);
				if (this.year.equalsIgnoreCase("DI4") || this.year.equalsIgnoreCase("DI5"))
					this.writeBooleanDI4(teacherEndRow, sheet, course.getType());
				teacherEndRow++;
				lastRow = teacherEndRow;

			}
			if (teacher.getHoursTP() != 0) {

				// Row row = sheet.createRow(teacherEndRow);
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "TP");

				cell.setCellStyle(StylesLib.tpStyle((XSSFWorkbook) workbook));

				this.writeTeacher(teacherEndRow, sheet, teacher.getName());
				this.writeHoursPut(teacherEndRow, sheet, teacher.getHoursTP());
				if (this.year.equalsIgnoreCase("DI3"))
					this.writeBooleanDI3(teacherEndRow, sheet, false);
				if (this.year.equalsIgnoreCase("DI4") || this.year.equalsIgnoreCase("DI5"))
					this.writeBooleanDI4(teacherEndRow, sheet, course.getType());
				teacherEndRow++;
				lastRow = teacherEndRow;

			}
			// il reste mundus

			if (teacher.getHoursCM() == 0 && teacher.getHoursTD() == 0 && teacher.getHoursTP() == 0
					&& teacher.getTDMundus() == 0 && teacher.getTPMundus() == 0) {

				// Row row = sheet.createRow(teacherEndRow);

				this.writeTeacher(teacherEndRow, sheet, teacher.getName());
				if (this.year.equalsIgnoreCase("DI3"))
					this.writeBooleanDI3(teacherEndRow, sheet, false);
				if (this.year.equalsIgnoreCase("DI4") || this.year.equalsIgnoreCase("DI5"))
					this.writeBooleanDI4(teacherEndRow, sheet, course.getType());
				teacherEndRow++;
				lastRow = teacherEndRow;
			}

			if (teacherStartRow < teacherEndRow - 1) {
				StylesLib.setCellMerge(sheet, teacherStartRow, teacherEndRow - 1, 2, 2);
			}
			teacherStartRow = teacherEndRow;
		}
		if (course.hasCt() || course.hasCc()) {
			if (course.hasCt() && course.hasCc()) {
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "CC/CT");
				cell.setCellStyle(StylesLib.ccStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 7, sheet, 4);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 6, sheet, 0);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
				if (this.year.equalsIgnoreCase("DI3"))
					this.writeBooleanDI3(teacherEndRow, sheet, false);
				if (this.year.equalsIgnoreCase("DI4") || this.year.equalsIgnoreCase("DI5"))
					this.writeBooleanDI4(teacherEndRow, sheet, course.getType());
				teacherEndRow++;

			} else if (course.hasCt()) {
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "CT");
				cell.setCellStyle(StylesLib.ccStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 7, sheet, 2);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 6, sheet, 0);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
				if (this.year.equalsIgnoreCase("DI3"))
					this.writeBooleanDI3(teacherEndRow, sheet, false);
				if (this.year.equalsIgnoreCase("DI4") || this.year.equalsIgnoreCase("DI5"))
					this.writeBooleanDI4(teacherEndRow, sheet, course.getType());
				teacherEndRow++;
			} else {
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "CC");
				cell.setCellStyle(StylesLib.ccStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 6, sheet, 2);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 7, sheet, 0);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
				if (this.year.equalsIgnoreCase("DI3"))
					this.writeBooleanDI3(teacherEndRow, sheet, false);
				if (this.year.equalsIgnoreCase("DI4") || this.year.equalsIgnoreCase("DI5"))
					this.writeBooleanDI4(teacherEndRow, sheet, course.getType());
				teacherEndRow++;
			}

			if (!course.getListTeachers().isEmpty()) {
				this.writeTeacher(teacherEndRow - 1, sheet, course.getListTeachers().get(0).getName());
			}

			lastRow = teacherEndRow;
		}
		return lastRow;
	}

	private void writeBooleanDI3(int row, Sheet sheet, Boolean mundus) {
		if (mundus) {
			Cell cell = super.writeNumberCell(row, 4, sheet, 1);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
			cell = super.writeNumberCell(row, 3, sheet, 0);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
		} else {
			Cell cell = super.writeNumberCell(row, 3, sheet, 1);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
			cell = super.writeNumberCell(row, 4, sheet, 0);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
		}

	}

	private void writeBooleanDI4(int row, Sheet sheet, String type) {
		if (type.equalsIgnoreCase("ASR")) {// right ASR
			Cell cell = super.writeNumberCell(row, 4, sheet, 1);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
			cell = super.writeNumberCell(row, 3, sheet, 0);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
		} else if (type.equalsIgnoreCase("SI")) {// left SI
			Cell cell = super.writeNumberCell(row, 3, sheet, 1);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
			cell = super.writeNumberCell(row, 4, sheet, 0);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
		} else {// all
			Cell cell = super.writeNumberCell(row, 3, sheet, 1);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
			cell = super.writeNumberCell(row, 4, sheet, 1);
			cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
		}

	}

	private void writeIntroPart(Planning planning) {
		lastWritenRow = 2;
		String calName = planning.getCalendar().getName();
		Sheet sheet = sheets.get(calName);
		Cell cell = super.writeStringCell(lastWritenRow, 0, sheet, this.year + " " + calName);
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));

		cell = super.writeFormula("TODAY()", lastWritenRow, 1, sheet);
		cell.setCellStyle(StylesLib.dateFormatStyle((XSSFWorkbook) workbook));

		lastWritenRow++;
		cell = super.writeStringCell(lastWritenRow, 0, sheet, planning.getYear());
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));

		lastWritenRow++;

		cell = super.writeStringCell(lastWritenRow, 2, sheet, "Disponibilité / étudiant (h)");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		lastWritenRow++;

		cell = super.writeStringCell(lastWritenRow, 2, sheet, "Créneaux disponibles");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		lastWritenRow++;

		cell = super.writeStringCell(lastWritenRow, 2, sheet, "Créneaux utilisés");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		lastWritenRow += 4;

		StylesLib.setCellMerge(sheet, lastWritenRow - 2, lastWritenRow, 2, 2);

		cell = super.writeStringCell(lastWritenRow - 2, 2, sheet, "Synthèse volume travail / étudiant (h)");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		lastWritenRow += 2;

		cell = super.writeStringCell(lastWritenRow, 2, sheet, "N° semaine");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		lastWritenRow++;

		cell = super.writeStringCell(lastWritenRow, 2, sheet, "Date semaine");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		lastWritenRow += 2;

		StylesLib.setCellMerge(sheet, lastWritenRow, lastWritenRow, 5, 7);
		cell = super.writeStringCell(lastWritenRow, 5, sheet, "Heures à placer");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		switch (nameYear) {

		case DI3:
			// TODO Ajouter colonnes mundus
			break;

		case DI4:

			cell = super.writeStringCell(lastWritenRow, 3, sheet, "SI");
			cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

			cell = super.writeStringCell(lastWritenRow, 4, sheet, "ASR");
			cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

			break;
			
		case DI5:
			// TODO Ajouter colonnes pour les options
			break;
			
		default:
			break;
		}

		lastWritenRow++;
	}

	private void writeTeachingUnit(int row, Sheet sheet, String content) {
		Cell cell = super.writeStringCell(row, 0, sheet, content);
		StylesLib.columTitleWidth(sheet, 0);
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
	}

	private void writeCourse(int row, Sheet sheet, String content) {
		Cell cell = super.writeStringCell(row, 1, sheet, content);
		StylesLib.columTitleWidth(sheet, 1);
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
	}

	private void writeTeacher(int row, Sheet sheet, String content) {
		Cell cell = super.writeStringCell(row, 2, sheet, content);
		StylesLib.columTitleWidth(sheet, 2);
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
	}

	private void writeHoursPut(int row, Sheet sheet, double content) {
		Cell cell = super.writeNumberCell(row, 5, sheet, (float) content);
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
	}

	private void writeWeeks(Planning planning, int numSemester) {
		Calendar calendar = planning.getCalendar();
		Semester semester = calendar.getListSemester().get(numSemester);
		Date startDate = semester.getStartDate();
		Date endDate = semester.getEndDate();

		LinkedHashMap<Integer, String> dates = ToolBox.getBetweenDates(startDate, endDate);
		String calName = planning.getCalendar().getName();
		int i = 11;
		for (Integer key : dates.keySet()) {

			StylesLib.setCellMerge(sheets.get(calName), lastWritenRow - 5, lastWritenRow - 5, i, i + 2);
			StylesLib.setCellMerge(sheets.get(calName), lastWritenRow - 4, lastWritenRow - 4, i, i + 2);
			// write weeks
			Cell cell1 = super.writeStringCell(lastWritenRow - 4, i, sheets.get(calName), dates.get(key));
			// write num of week
			Cell cell2 = super.writeNumberCell(lastWritenRow - 5, i, sheets.get(calName), key);

			if (!ToolBox.isHoliday(dates.get(key), semester.getListHoliday())) {// if
																				// not
																				// holiday
				// write summary
				this.writeSummary(i, sheets.get(calName), numSemester);
				// write cm,td,tp
				Cell cell3 = super.writeStringCell(lastWritenRow - 2, i, sheets.get(calName), "CM");
				Cell cell4 = super.writeStringCell(lastWritenRow - 2, i + 1, sheets.get(calName), "TD");
				Cell cell5 = super.writeStringCell(lastWritenRow - 2, i + 2, sheets.get(calName), "TP");
				cell3.setCellStyle(StylesLib.cmStyle((XSSFWorkbook) workbook));
				cell4.setCellStyle(StylesLib.tdStyle((XSSFWorkbook) workbook));
				cell5.setCellStyle(StylesLib.tpStyle((XSSFWorkbook) workbook));
				cell1.setCellStyle(StylesLib.dateStyle((XSSFWorkbook) workbook));
				cell2.setCellStyle(StylesLib.weekNumStyle((XSSFWorkbook) workbook));
			} else {
				cell1.setCellStyle(StylesLib.holidayStyle((XSSFWorkbook) workbook));
				cell2.setCellStyle(StylesLib.holidayStyle((XSSFWorkbook) workbook));
			}
			// set style
			sheets.get(calName).setColumnWidth(i, 60 * 20);
			sheets.get(calName).setColumnWidth(i + 1, 60 * 20);
			sheets.get(calName).setColumnWidth(i + 2, 60 * 20);
			i += 3;

			//
		}

	}

	private void writeSummary(int col, Sheet sheet, int numSemester) {
		String rowStrStart, rowStrEnd;
		String totalColStart, totalColEnd;

		// total
		totalColStart = ToolBox.excelColIndexToStr(col + 1) + (this.lastWritenRow - 7);
		totalColEnd = ToolBox.excelColIndexToStr(col + 3) + (this.lastWritenRow - 7);
		StylesLib.setCellMerge(sheet, lastWritenRow - 7, lastWritenRow - 7, col, col + 2);
		Cell cell4_1 = super.writeFormula("SUM(" + totalColStart + ":" + totalColEnd + ")", lastWritenRow - 7, col,
				sheet);
		// each part
		Cell cell1 = super.writeStringCell(lastWritenRow - 9, col, sheet, "CM");
		rowStrStart = ToolBox.excelColIndexToStr(col + 1) + (this.lastWritenRow + 1);
		rowStrEnd = ToolBox.excelColIndexToStr(col + 1) + (this.lastTURow[numSemester] + 1);

		// generate variable
		String array1 = rowStrStart + ":" + rowStrEnd;
		String array2_1 = ToolBox.excelColIndexToStr(4) + (this.lastWritenRow + 1) + ":" + ToolBox.excelColIndexToStr(4)
				+ (this.lastTURow[numSemester] + 1);
		// write SUMPRODUCT()
		Cell cell1_1 = super.writeFormula("SUMPRODUCT(" + array1 + "," + array2_1 + ")", lastWritenRow - 8, col++,
				sheet);

		// each part
		Cell cell2 = super.writeStringCell(lastWritenRow - 9, col, sheet, "TD");
		rowStrStart = ToolBox.excelColIndexToStr(col + 1) + (this.lastWritenRow + 1);
		rowStrEnd = ToolBox.excelColIndexToStr(col + 1) + (this.lastTURow[numSemester] + 1);
		// generate variable
		array1 = rowStrStart + ":" + rowStrEnd;
		array2_1 = ToolBox.excelColIndexToStr(4) + (this.lastWritenRow + 1) + ":" + ToolBox.excelColIndexToStr(4)
				+ (this.lastTURow[numSemester] + 1);
		// write SUMPRODUCT()
		Cell cell2_1 = super.writeFormula("SUMPRODUCT(" + array1 + "," + array2_1 + ")", lastWritenRow - 8, col++,
				sheet);

		// each part
		Cell cell3 = super.writeStringCell(lastWritenRow - 9, col, sheet, "TP");
		rowStrStart = ToolBox.excelColIndexToStr(col + 1) + (this.lastWritenRow + 1);
		rowStrEnd = ToolBox.excelColIndexToStr(col + 1) + (this.lastTURow[numSemester] + 1);
		// generate variable
		array1 = rowStrStart + ":" + rowStrEnd;
		array2_1 = ToolBox.excelColIndexToStr(4) + (this.lastWritenRow + 1) + ":" + ToolBox.excelColIndexToStr(4)
				+ (this.lastTURow[numSemester] + 1);
		// write SUMPRODUCT()
		Cell cell3_1 = super.writeFormula("SUMPRODUCT(" + array1 + "," + array2_1 + ")", lastWritenRow - 8, col++,
				sheet);

		cell1.setCellStyle(StylesLib.cmStyle((XSSFWorkbook) workbook));
		cell2.setCellStyle(StylesLib.tdStyle((XSSFWorkbook) workbook));
		cell3.setCellStyle(StylesLib.tpStyle((XSSFWorkbook) workbook));

		cell1_1.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
		cell2_1.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
		cell3_1.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
		cell4_1.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));

		/*
		 * =(J10/Paramétrage!$C$5)+(IF(MOD(K10,Paramétrage!$C$5*Paramétrage!$E$2 ),
		 * (ROUNDDOWN(K10/(Paramétrage!$C$5*Paramétrage!$E$2),0)+1)*Paramétrage! $E$2,
		 * ROUNDDOWN(K10/(Paramétrage!$C$5*Paramétrage!$E$2)*Paramétrage!$E$2,0) ))
		 * +(IF(MOD(L10,Paramétrage!$C$5*Paramétrage!$F$2),
		 * (ROUNDDOWN(L10/(Paramétrage!$C$5*Paramétrage!$F$2),0)+1)*Paramétrage! $E$2,
		 * ROUNDDOWN(L10/(Paramétrage!$C$5*Paramétrage!$F$2)*Paramétrage!$F$2,0) ))
		 */
	}
}
