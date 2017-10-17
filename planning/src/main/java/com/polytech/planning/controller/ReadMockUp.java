package com.polytech.planning.controller;

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
	private boolean readCourse(int sheetNum, int rowNum, int colNum) {
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
	 * @param sheetNum
	 */
	public void readCourses(int sheetNum) {
		int[] coordinates = new int[2]; // [0] ligne -[1] colonne
		coordinates = searchContent(sheetNum, "UNITE D'ENSEIGNEMENT");
		
		// Tant que deux lignes de suites ne sont pas vide alors
			// Ligne ++ et if (!cell is empty || !cell is numeric) 
				// readCourse()
	}	
}
