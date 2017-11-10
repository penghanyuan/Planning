package com.polytech.planning.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.polytech.planning.model.Course;
import com.polytech.planning.model.Planning;
import com.polytech.planning.model.Teacher;
import com.polytech.planning.model.TeachingUnit;

public class WritePlanning extends WriteFile {

	private Planning planning;
	private Workbook workbook;

	/**
	 * Constructor
	 * 
	 * @param filePath
	 */
	public WritePlanning(Planning planning, String filePath) {
		super(filePath);
		this.planning = planning;
		this.workbook = super.getWorkbook();
	}

	/**
	 * Create Planning in to excel file.
	 * 
	 * @param planning
	 */
	public void createFile() {
		// System.out.println(planning.getTeachingUnits().isEmpty());
		this.writeTeachingUnits();
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

	public void writeTeachingUnits() {
		List<TeachingUnit> teachingUnits = this.planning.getTeachingUnits();
		int courseStartRow = 17, teachingUnitStartRow = 17;
		Row lastRow;
		Sheet sheet = this.workbook.createSheet("Planning S5");
		for (TeachingUnit teachingUnit : teachingUnits) {

			lastRow = this.writeCourses(courseStartRow, sheet, teachingUnit);
			// Row row = sheet.createRow(courseEndRow);

			if (teachingUnitStartRow < lastRow.getRowNum())
				StylesLib.setCellMerge(sheet, teachingUnitStartRow, lastRow.getRowNum(), 0, 0);
			this.writeTeachingUnit(sheet.getRow(teachingUnitStartRow), sheet, teachingUnit.getName());
			teachingUnitStartRow = lastRow.getRowNum() + 1;
			courseStartRow = teachingUnitStartRow;
		}
	}

	public Row writeCourses(int courseStartRow, Sheet sheet, TeachingUnit teachingUnit) {
		int courseEndRow = courseStartRow;
		Row nowRow, lastRow = sheet.createRow(courseStartRow);
		for (Course course : teachingUnit.getListCourses()) {

			nowRow = this.writeTeachers(courseStartRow, sheet, course);

			courseEndRow = nowRow.getRowNum();
			int indexMerge = 0;
			if (courseStartRow < courseEndRow) {
				indexMerge = StylesLib.setCellMerge(sheet, courseStartRow, courseEndRow, 1, 1);
			}
			this.writeCourse(sheet.getRow(courseStartRow), sheet, course.getName());
			courseStartRow = nowRow.getRowNum() + 1;
			// courseEndRow = courseStartRow;
			lastRow = nowRow;
		}
		return lastRow;
	}

	private Row writeTeachers(int teacherStartRow, Sheet sheet, Course course) {
		int teacherEndRow = teacherStartRow;
		Row lastRow = null;
		for (Teacher teacher : course.getListTeachers()) {

			if (teacher.getHoursCM() != 0) {
				Row row = sheet.createRow(teacherEndRow);
				Cell cell = super.writeCell(row, 7, sheet, "CM");
				cell.setCellStyle(StylesLib.cmStyle((XSSFWorkbook) workbook));
				
				this.writeTeacher(row, sheet, teacher.getName());
				teacherEndRow++;
				lastRow = row;

			}
			if (teacher.getHoursTD() != 0) {

				Row row = sheet.createRow(teacherEndRow);
				Cell cell = super.writeCell(row, 7, sheet, "TD");
				cell.setCellStyle(StylesLib.tdStyle((XSSFWorkbook) workbook));
				
				this.writeTeacher(row, sheet, teacher.getName());
				teacherEndRow++;
				lastRow = row;
				
			}
			if (teacher.getHoursTP() != 0) {

				Row row = sheet.createRow(teacherEndRow);
				Cell cell = super.writeCell(row, 7, sheet, "TP");
				cell.setCellStyle(StylesLib.tpStyle((XSSFWorkbook) workbook));

				this.writeTeacher(row, sheet, teacher.getName());
				teacherEndRow++;
				lastRow = row;
				
			}
			// il reste mundus
			
			if (teacher.getHoursCM() == 0 && teacher.getHoursTD() == 0 && teacher.getHoursTP() == 0) {

				Row row = sheet.createRow(teacherEndRow);

				this.writeTeacher(row, sheet, teacher.getName());
				teacherEndRow++;
				lastRow = row;
			}

			if (teacherStartRow < teacherEndRow - 1)
				StylesLib.setCellMerge(sheet, teacherStartRow, teacherEndRow - 1, 2, 2);
			teacherStartRow = teacherEndRow;
		}
		return lastRow;
	}

	public void writeTeachingUnit(Row row, Sheet sheet, String content) {
		Cell cell = super.writeCell(row, 0, sheet, content);
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
	}

	private void writeCourse(Row row, Sheet sheet, String content) {
		Cell cell = super.writeCell(row, 1, sheet, content);
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
	}

	private void writeTeacher(Row row, Sheet sheet, String content) {
		Cell cell = super.writeCell(row, 2, sheet, content);
		cell.setCellStyle(StylesLib.baseStyle((XSSFWorkbook) workbook));
	}
}
