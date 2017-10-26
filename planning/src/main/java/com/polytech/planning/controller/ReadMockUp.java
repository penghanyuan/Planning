package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.omg.CORBA.DynAnyPackage.InvalidValue;

import com.polytech.planning.model.OriginalCourse;

public class ReadMockUp extends ReadFile {

	private LinkedHashMap<String, List<OriginalCourse>> teachingUnits;
	private int sheetNum;
	private int rowNum;
	private int colNum;

	/**
	 * Constructor
	 * 
	 * @param filePath
	 * @param sheetNum
	 */
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
	 * Method to read all teaching units
	 */
	public void readTeachingUnits() {
		boolean notFinish = true;
		int nbLoop = 0;

		while (notFinish) {
			if ((cellIsEmpty(rowNum, colNum, sheetNum) || cellIsNumeric(rowNum, colNum, sheetNum))
					&& (cellIsEmpty(rowNum, colNum - 1, sheetNum) || cellIsNumeric(rowNum, colNum - 1, sheetNum))) {

				System.out.println("Ligne " + rowNum + " - Colonne " + colNum);
				nbLoop++;
				rowNum++;

				if (nbLoop > 1)
					notFinish = false;

				System.out.println(nbLoop);
			} else {
				System.out.println("Ligne " + rowNum + " - Colonne " + colNum);
				readTeachingUnit();
			}
			System.out.println(notFinish);
		}
	}

	/**
	 * Method to read one Teaching Unit and add it in teachingUnits
	 * LinkedHashMap
	 */
	private void readTeachingUnit() {
		String name = null;
		List<OriginalCourse> listCourses;
		int colValue = colNum;

		rowNum++;
		colNum--;
		if (cellIsEmpty(rowNum, colNum, sheetNum) || cellIsNumeric(rowNum, colNum, sheetNum)) {
			while (cellIsEmpty(rowNum, colNum, colNum) || cellIsNumeric(rowNum, colNum, sheetNum)) {
				rowNum++;
			}
		}

		name = readCell(rowNum, colNum, sheetNum);
		name = ToolBox.capitalize(name);

		colNum = colValue;

		try {
			listCourses = readCourses();
			teachingUnits.put(name, listCourses);
			System.out.println(ToolBox.listToString(listCourses));
		} catch (InvalidValue e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to read one course
	 */
	private OriginalCourse readCourse() {
		OriginalCourse buffer = new OriginalCourse();
		String mundus = "Mundus";
		String readMundus;
		int colValue = colNum;

		mundus = normalizeText(mundus);

		buffer.setCourseName(readCell(rowNum, colNum, sheetNum)); // C -> name

		buffer.setHoursCM(readNumericCell(rowNum, ++colNum, sheetNum)); // D ->
																		// CM
		buffer.setHoursTD(readNumericCell(rowNum, ++colNum, sheetNum)); // E ->
																		// TD
		buffer.setHoursTP(readNumericCell(rowNum, ++colNum, sheetNum)); // F ->
																		// TP
		buffer.setHoursProject(readNumericCell(rowNum, ++colNum, sheetNum)); // G
																				// ->
																				// Project

		colNum = colNum + 6;

		readMundus = readCell(rowNum, colNum, sheetNum); // M -> Mundus
		readMundus = normalizeText(readMundus);

		if (readMundus.equals(mundus))
			buffer.setMundus(true);
		else
			buffer.setMundus(false);

		buffer.setTeachers(readCell(rowNum, ++colNum, sheetNum)); // N ->
																	// Teachers

		colNum = colValue;
		return buffer;
	}

	/**
	 * Method to read all courses in a Teaching Unit
	 * 
	 * @throws InvalidValue
	 */
	public List<OriginalCourse> readCourses() throws InvalidValue {
		int blankLines = 0;
		int rowDiff = 0;
		List<OriginalCourse> courses = new ArrayList<OriginalCourse>();

		if (rowNum >= 0 && colNum >= 0) {
			while (blankLines < 2) {
				if (!cellIsEmpty(rowNum, colNum, sheetNum)) {
					blankLines = 0;
					courses.add(readCourse());
					rowDiff = 0;
				} else {
					blankLines++;
					rowDiff++;
				}
				rowNum++;
			}
		} else {
			throw new InvalidValue("Les coordonnées ne peuvent pas être inferieurs à 0");
		}

		rowNum = rowNum - rowDiff;
		return courses;
	}

}
