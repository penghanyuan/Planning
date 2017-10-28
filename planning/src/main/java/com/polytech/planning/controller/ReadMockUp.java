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

	private int colTitleTU;
	private int colCourseName;
	private int colCM;
	private int colTD;
	private int colTP;
	private int colProject;
	private int colMundus;
	private int colTeachers;

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

		this.colCourseName = coordinates[1];
		this.colTitleTU = coordinates[1] - 1;

		this.colCM = getColNum(sheetNum, "Cours", false);
		this.colTD = getColNum(sheetNum, "TD", false);
		this.colTP = getColNum(sheetNum, "TP", false);

		this.colProject = getColNum(sheetNum, "Projet", false);
		this.colMundus = getColNum(sheetNum, "Mundus", false);

		this.colTeachers = getColNum(sheetNum, "Affectation enseignement et responsabilité UE", true);

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

				System.out.println("Cellule " + rowNum + " - " + colNum + " est de type "
						+ getCellType(rowNum, colNum, sheetNum).toString());

				nbLoop++;
				rowNum++;

				if (nbLoop > 1)
					notFinish = false;
			} else {
				nbLoop = 0;
				readTeachingUnit();
			}
		}
	}

	/**
	 * Method to read one Teaching Unit and add it in teachingUnits LinkedHashMap
	 */
	private void readTeachingUnit() {
		String name = null;
		List<OriginalCourse> listCourses;
		int colValue = colNum;

		rowNum++;
		colNum--;

		if (cellIsEmpty(rowNum, colNum, sheetNum) || cellIsNumeric(rowNum, colNum, sheetNum)) {
			while (cellIsEmpty(rowNum, colNum + 1, colNum) || cellIsNumeric(rowNum, colNum + 1, sheetNum)) {
				rowNum++;
			}
		}

		name = readCell(rowNum - 1, colNum, sheetNum);

		//System.out.println("rowNum: " + (rowNum - 1) + ", colNum:" + colNum);
		//System.out.println("name is : " + name);

		name = ToolBox.capitalize(name);
		colNum = colValue;

		try {
			listCourses = readCourses();
			teachingUnits.put(name, listCourses);

			System.out.println("name: " + name);
			System.out.println(ToolBox.listToString(listCourses));

		} catch (InvalidValue e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to read one course
	 * 
	 * @return
	 */
	private OriginalCourse readCourse() {
		OriginalCourse buffer = new OriginalCourse();
		String mundus = "Mundus";
		String readMundus;
		int colValue = colNum;

		mundus = normalizeText(mundus);

		buffer.setCourseName(readCell(rowNum, colNum, sheetNum)); // C -> name

		buffer.setHoursCM(readNumericCell(rowNum, ++colNum, sheetNum)); // D -> CM
		buffer.setHoursTD(readNumericCell(rowNum, ++colNum, sheetNum)); // E -> TD
		buffer.setHoursTP(readNumericCell(rowNum, ++colNum, sheetNum)); // F -> TP
		buffer.setHoursProject(readNumericCell(rowNum, ++colNum, sheetNum)); // G -> Project

		colNum = colNum + 6;

		readMundus = readCell(rowNum, colNum, sheetNum); // M -> Mundus
		readMundus = normalizeText(readMundus);

		if (readMundus.equals(mundus))
			buffer.setMundus(true);
		else
			buffer.setMundus(false);

		buffer.setTeachers(readCell(rowNum, ++colNum, sheetNum)); // N ->

		colNum = colValue;
		return buffer;
	}

	/**
	 * Method to read all courses in a Teaching Unit
	 * 
	 * @return
	 * @throws InvalidValue
	 */
	public List<OriginalCourse> readCourses() throws InvalidValue {
		int blankLines = 0;
		int rowDiff = 0;
		List<OriginalCourse> courses = new ArrayList<OriginalCourse>();

		if (rowNum >= 0 && colNum >= 0) {
			while (blankLines < 1) {
				if (cellIsEmpty(rowNum, colNum, sheetNum)) {
					blankLines++;
					rowDiff++;
				} else if (cellIsNumeric(rowNum, colNum, sheetNum)) {
					blankLines++;
					rowDiff++;
				} else {
					blankLines = 0;
					courses.add(readCourse());
					rowDiff = 0;
				}
				rowNum++;
			}
		} else {
			throw new InvalidValue("Les coordonnées ne peuvent pas être inferieurs à 0");
		}

		System.out.println("rowNum: " + (rowNum) + ", rowDiff:" + rowDiff);
		// rowNum = rowNum - rowDiff;
		return courses;
	}

	/**
	 * Method to get the list of teaching units
	 * 
	 * @return the list of teaching units in a LinkedHashMap with the name of the
	 *         teaching units and the list of courses present in this teaching unit.
	 */
	public LinkedHashMap<String, List<OriginalCourse>> getTeachingUnits() {
		return this.teachingUnits;
	}
}
