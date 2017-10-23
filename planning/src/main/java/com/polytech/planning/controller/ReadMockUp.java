package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.omg.CORBA.DynAnyPackage.InvalidValue;

import com.polytech.planning.model.OriginalCourse;

public class ReadMockUp extends ReadFile {

	private LinkedHashMap<String, List<OriginalCourse>> teachingUnits;
	private int sheetNum;
	// store the num of actual row and col
	private int rowNum;
	private int colNum;

	public ReadMockUp(String filePath, int sheetNum) {
		super(filePath);
		String searchString = "Unité d'enseignement";
		int[] coordinates = searchContent(sheetNum, searchString);
		
		this.sheetNum = sheetNum;
		this.rowNum = coordinates[0];
		this.colNum = coordinates[1];
		
		teachingUnits = new LinkedHashMap<String, List<OriginalCourse>>();
	}

	/**
	 * Method to read one Teaching Unit and add it in teachingUnits LinkedHashMap
	 * 
	 * @param rowNum
	 *            The row number
	 * @param colNum
	 *            The column number
	 */
	public void readTeachingUnit(int rowNum, int colNum) {
		String name = null;
		List<OriginalCourse> listCourses;

		name = readCell(rowNum, colNum, sheetNum);

		try {
			listCourses = readCourses(rowNum, colNum);
			teachingUnits.put(name, listCourses);
		} catch (InvalidValue e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to read one course
	 * 
	 * @param rowNum
	 *            The row number
	 * @param colNum
	 *            The column number
	 */
	private OriginalCourse readCourse(int rowNum, int colNum) {
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
	 *            The row number
	 * @param colNum
	 *            The column number
	 * @throws InvalidValue
	 */
	public List<OriginalCourse> readCourses(int rowNum, int colNum) throws InvalidValue {
		int blankLines = 0;
		List<OriginalCourse> courses = new ArrayList<OriginalCourse>();

		if (rowNum >= 0 && colNum >= 0) {
			while (blankLines < 2) {

				System.out.println(blankLines);
				if (!cellIsEmpty(rowNum, colNum, sheetNum)) {
					blankLines = 0;
					courses.add(readCourse(rowNum, colNum));
				} else {
					blankLines++;
				}
				rowNum++;
			}
		} else {
			throw new InvalidValue("Les coordonnées ne peuvent pas être inferieurs à 0");
		}
		return courses;
	}

}
