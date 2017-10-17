package com.polytech.planning.controller;

import org.omg.CORBA.DynAnyPackage.InvalidValue;

import com.polytech.planning.model.OriginalCourse;
import com.polytech.planning.model.OriginalTeachingUnit;

public class ReadMockUp extends ReadFile {

	private OriginalTeachingUnit courses;

	public ReadMockUp(String filePath) {
		super(filePath);
		courses = new OriginalTeachingUnit();
	}

	/**
	 * @param sheetNum
	 * @param rowNum
	 * @param colNum
	 */
	private boolean readCourse(int rowNum, int colNum, int sheetNum) {
		OriginalCourse buffer = new OriginalCourse();
		String mundus = "Mundus";
		String readMundus;

		mundus = normalizeText(mundus);

		buffer.setCourseName(readCell(rowNum, colNum, sheetNum)); // C -> name

		buffer.setHoursCM(readNumericCell(rowNum, ++colNum, sheetNum)); // D -> CM
		buffer.setHoursTD(readNumericCell(rowNum, ++colNum, sheetNum)); // E -> TD
		buffer.setHoursTP(readNumericCell(rowNum, ++colNum, sheetNum)); // F -> TP
		buffer.setHoursProject(readNumericCell(rowNum, ++colNum, sheetNum)); // G -> Project

		colNum = colNum + 5;

		readMundus = readCell(rowNum, colNum, sheetNum); // M -> Mundus
		readMundus = normalizeText(readMundus);

		if (readMundus.equals(mundus))
			buffer.setMundus(true);
		else
			buffer.setMundus(false);

		buffer.setTeachers(readCell(rowNum, ++colNum, sheetNum)); // N -> Teachers

		return courses.addCourse(buffer);
	}

	/**
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param sheetNum
	 * @throws InvalidValue
	 */
	public void readCourses(int rowNum, int colNum, int sheetNum) throws InvalidValue {
		int blankLines = 0;

		if(rowNum >= 0 && colNum >= 0) {
			while(blankLines < 2) {
				
				System.out.println(blankLines);
				if(!cellIsEmpty(rowNum, colNum, sheetNum)) {
					blankLines = 0;
					readCourse(rowNum, colNum, sheetNum);
				} else {
					blankLines++;
				}
				rowNum++;
			}
		} else {
			throw new InvalidValue("Les coordonnées ne peuvent être inferieurs à 0");
		}
	}

	/**
	 * @return the courses
	 */
	public OriginalTeachingUnit getTeachingUnit() {
		return courses;
	}

}
