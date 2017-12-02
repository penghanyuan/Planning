package com.polytech.planning.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.polytech.planning.model.Course;
import com.polytech.planning.model.Planning;
import com.polytech.planning.model.Teacher;
import com.polytech.planning.model.TeachingUnit;

public class WritePlanning extends WriteFile {

	private List<Planning> plannings;
	private Workbook workbook;
	private String year;
	private HashMap<String, Sheet> sheets;
	private int lastWritenRow; // derniere ligne ecrite
	private int[] lastTURow;// 0=> last row of teaching unit in sheet1, 1=> ...
							// in sheet 2

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
			System.out.println(this.lastTURow[i]);
		}

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
				teacherEndRow++;

			} else if (course.hasCt()) {
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "CT");
				cell.setCellStyle(StylesLib.ccStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 7, sheet, 2);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 6, sheet, 0);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
				teacherEndRow++;
			} else {
				Cell cell = super.writeStringCell(teacherEndRow, 9, sheet, "CC");
				cell.setCellStyle(StylesLib.ccStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 6, sheet, 2);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
				cell = super.writeNumberCell(teacherEndRow, 7, sheet, 0);
				cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
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

	private void writeIntroPart(Planning planning) {

		// ligne 3 col 0 Année
		lastWritenRow = 2;
		String calName = planning.getCalendar().getName();
		Cell cell = super.writeStringCell(lastWritenRow, 0, sheets.get(calName), this.year + " " + calName);
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));

		cell = super.writeFormula("TODAY()", lastWritenRow, 1, sheets.get(calName));
		cell.setCellStyle(StylesLib.dateFormatStyle((XSSFWorkbook) workbook));

		lastWritenRow++;
		cell = super.writeStringCell(lastWritenRow, 0, sheets.get(calName), planning.getYear());
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));

		lastWritenRow++;

		cell = super.writeStringCell(lastWritenRow, 2, sheets.get(calName), "Disponibilité / étudiant (h)");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		lastWritenRow++;

		cell = super.writeStringCell(lastWritenRow, 2, sheets.get(calName), "Créneaux disponibles");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		lastWritenRow++;

		cell = super.writeStringCell(lastWritenRow, 2, sheets.get(calName), "Créneaux utilisés");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

		lastWritenRow++;
		lastWritenRow++;

		cell = super.writeStringCell(lastWritenRow, 2, sheets.get(calName), "Synthèse volume travail / étudiant (h)");
		cell.setCellStyle(StylesLib.baseBorderStyle((XSSFWorkbook) workbook));

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
}
