package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.omg.CORBA.DynAnyPackage.InvalidValue;

import com.polytech.planning.model.OriginalCourse;

public class ReadMockUp extends ReadFile {

	private LinkedHashMap<String, List<OriginalCourse>> teachingUnits;
	private int sheetNum;
	private int rowNum;

	private int colTitleTU;
	private int colCourseName;
	private int colCM;
	private int colTD;
	private int colTP;
	private int colProject;
	private int colMundus;
	private int colCC;
	private int colCT;
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
		int[] coordinates = searchContent(sheetNum, searchString, false);
		
		if(coordinates[0] == -1 || coordinates[1] == -1) {
			try {
				throw new FileFormatException("Le formats du fichier "+ filePath +" n'est pas valide au niveau de la maquette. Il manque le terme 'Unité d'enseignement'.");
			} catch (FileFormatException e) {
				e.printStackTrace();
			}
		}

		this.sheetNum = sheetNum;
		this.rowNum = coordinates[0];

		this.colCourseName = coordinates[1];
		this.colTitleTU = coordinates[1] - 1;

		this.colCM = searchContent(sheetNum, "Cours", false)[1];
		if(coordinates[0] == -1 || coordinates[1] == -1) {
			try {
				throw new FileFormatException("Le formats du fichier "+ filePath +" n'est pas valide au niveau de la maquette. Il manque le terme 'Cours'.");
			} catch (FileFormatException e) {
				e.printStackTrace();
			}
		}
		
		this.colTD = searchContent(sheetNum, "TD", false)[1];
		if(coordinates[0] == -1 || coordinates[1] == -1) {
			try {
				throw new FileFormatException("Le formats du fichier "+ filePath +" n'est pas valide au niveau de la maquette. Il manque le terme 'TD'.");
			} catch (FileFormatException e) {
				e.printStackTrace();
			}
		}
		
		this.colTP = searchContent(sheetNum, "TP", false)[1];
		if(coordinates[0] == -1 || coordinates[1] == -1) {
			try {
				throw new FileFormatException("Le formats du fichier "+ filePath +" n'est pas valide au niveau de la maquette. Il manque le terme 'TP'.");
			} catch (FileFormatException e) {
				e.printStackTrace();
			}
		}
		
		this.colProject = searchContent(sheetNum, "Projet", false)[1];
		if(coordinates[0] == -1 || coordinates[1] == -1) {
			try {
				throw new FileFormatException("Le formats du fichier "+ filePath +" n'est pas valide au niveau de la maquette. Il manque le terme 'Projet'.");
			} catch (FileFormatException e) {
				e.printStackTrace();
			}
		}

		this.colCC = searchContent(sheetNum, "CC", false)[1];
		if(coordinates[0] == -1 || coordinates[1] == -1) {
			try {
				throw new FileFormatException("Le formats du fichier "+ filePath +" n'est pas valide au niveau de la maquette. Il manque le terme 'CC'.");
			} catch (FileFormatException e) {
				e.printStackTrace();
			}
		}
		
		this.colCT = searchContent(sheetNum, "CT", false)[1];
		if(coordinates[0] == -1 || coordinates[1] == -1) {
			try {
				throw new FileFormatException("Le formats du fichier "+ filePath +" n'est pas valide au niveau de la maquette. Il manque le terme 'CT'.");
			} catch (FileFormatException e) {
				e.printStackTrace();
			}
		}

		this.colMundus = searchContent(sheetNum, "Mundus", false)[1];
		if(coordinates[0] == -1 || coordinates[1] == -1) {
			try {
				throw new FileFormatException("Le formats du fichier "+ filePath +" n'est pas valide au niveau de la maquette. Il manque le terme 'Mundus'.");
			} catch (FileFormatException e) {
				e.printStackTrace();
			}
		}

		this.colTeachers = searchContent(sheetNum, "Affectation enseignement et responsabilité UE", true)[1];
		if(coordinates[0] == -1 || coordinates[1] == -1) {
			try {
				throw new FileFormatException("Le formats du fichier "+ filePath +" n'est pas valide au niveau de la maquette. Il manque le terme 'Affectation enseignement et responsabilité UE'.");
			} catch (FileFormatException e) {
				e.printStackTrace();
			}
		}

		teachingUnits = new LinkedHashMap<String, List<OriginalCourse>>();
	}

	/**
	 * Method to read all teaching units
	 */
	public void readTeachingUnits() {
		boolean notFinish = true;
		int nbLoop = 0;

		while (notFinish) {

			if ((cellIsEmpty(rowNum, colCourseName, sheetNum) || cellIsNumeric(rowNum, colCourseName, sheetNum))
					&& (cellIsEmpty(rowNum, colTitleTU, sheetNum) || cellIsNumeric(rowNum, colTitleTU, sheetNum))) {

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

		rowNum++;

		if (cellIsEmpty(rowNum, colTitleTU, sheetNum) || cellIsNumeric(rowNum, colTitleTU, sheetNum)) {
			while (cellIsEmpty(rowNum, colCourseName, sheetNum) || cellIsNumeric(rowNum, colCourseName, sheetNum)) {
				rowNum++;
			}
		}

		name = readCell(rowNum - 1, colTitleTU, sheetNum);
		name = ToolBox.capitalize(name);

		try {
			listCourses = readCourses();
			teachingUnits.put(name, listCourses);
		} catch (InvalidValue e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to read one course
	 * 
	 * @return one OriginalCourse object
	 */
	private OriginalCourse readCourse() {
		OriginalCourse buffer = new OriginalCourse();
		String mundus = "Mundus";
		String readMundus;

		mundus = normalizeText(mundus);

		buffer.setCourseName(readCell(rowNum, colCourseName, sheetNum)); // C -> name

		buffer.setHoursCM(readNumericCell(rowNum, colCM, sheetNum)); // D -> CM
		buffer.setHoursTD(readNumericCell(rowNum, colTD, sheetNum)); // E -> TD
		buffer.setHoursTP(readNumericCell(rowNum, colTP, sheetNum)); // F -> TP

		if (colProject != -1) {
			buffer.setHoursProject(readNumericCell(rowNum, colProject, sheetNum)); // G -> Project
		}

		if (colCC != -1) {
			if ((cellIsNumeric(rowNum, colCC, sheetNum) && readNumericCell(rowNum, colCC, sheetNum) != 0) || cellIsString(rowNum, colCC, sheetNum) || cellIsEmpty(rowNum, colCC, sheetNum)) {
				buffer.setCc(true);
			}
		}

		if (colCT != -1) {
			if ((cellIsNumeric(rowNum, colCT, sheetNum) && readNumericCell(rowNum, colCT, sheetNum) != 0) || cellIsString(rowNum, colCT, sheetNum) || cellIsEmpty(rowNum, colCT, sheetNum)) {
				buffer.setCt(true);
			}
		}

		if (colMundus != -1) {
			readMundus = readCell(rowNum, colMundus, sheetNum); // M -> Mundus
			readMundus = normalizeText(readMundus);
			if (readMundus.equals(mundus))
				buffer.setMundus(true);
		}

		buffer.setTeachers(readCell(rowNum, colTeachers, sheetNum)); // N -> Teachers

		return buffer;
	}

	/**
	 * Method to read all courses in a Teaching Unit
	 * 
	 * @return a list of courses
	 * @throws InvalidValue
	 */
	public List<OriginalCourse> readCourses() throws InvalidValue {
		int blankLines = 0;
		List<OriginalCourse> courses = new ArrayList<OriginalCourse>();

		if (rowNum >= 0 && colCourseName >= 0) {
			while (blankLines < 1) {
				if (cellIsEmpty(rowNum, colCourseName, sheetNum)) {
					blankLines++;
				} else if (cellIsNumeric(rowNum, colCourseName, sheetNum)) {
					blankLines++;
				} else {
					blankLines = 0;
					courses.add(readCourse());
				}
				rowNum++;
			}
		} else {
			throw new InvalidValue("Les coordonnées ne peuvent pas être inferieurs à 0");
		}
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
