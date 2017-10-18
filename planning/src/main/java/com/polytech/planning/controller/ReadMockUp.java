package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.omg.CORBA.DynAnyPackage.InvalidValue;

import com.polytech.planning.model.OriginalCourse;

public class ReadMockUp extends ReadFile {

	private HashMap<String, List<OriginalCourse>> teachingUnits;

	public ReadMockUp(String filePath) {
		super(filePath);
		teachingUnits = new HashMap<String, List<OriginalCourse>>();
	}

	/**
	 * Method to read one course
	 * 
	 * @param sheetNum
	 * @param rowNum
	 * @param colNum
	 */
	private OriginalCourse readCourse(int rowNum, int colNum, int sheetNum) {
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

		return buffer;
	}

	/**
	 * Method to read all courses in a Teaching Unit
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param sheetNum
	 * @throws InvalidValue
	 */
	public List<OriginalCourse> readCourses(int rowNum, int colNum, int sheetNum) throws InvalidValue {
		int blankLines = 0;
		List<OriginalCourse> courses = new ArrayList<OriginalCourse>();

		if (rowNum >= 0 && colNum >= 0) {
			while (blankLines < 2) {

				System.out.println(blankLines);
				if (!cellIsEmpty(rowNum, colNum, sheetNum)) {
					blankLines = 0;
					courses.add(readCourse(rowNum, colNum, sheetNum));
				} else {
					blankLines++;
				}
				rowNum++;
			}
		} else {
			throw new InvalidValue("Les coordonnées ne peuvent être inferieurs à 0");
		}
		return courses;
	}

}
