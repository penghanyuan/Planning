package com.polytech.planning.controller;

import java.util.Date;
import java.util.HashMap;

import javax.naming.NameNotFoundException;

import com.polytech.planning.model.OriginalCalendar;

public class ReadCalendar extends ReadFile {

	private OriginalCalendar calendar;

	public ReadCalendar(String filePath) {
		super(filePath);
		calendar = new OriginalCalendar();
	}

	private HashMap<String, Date[]> readSemester(int sheetNum, String name) throws NameNotFoundException {
		String nameSemester;
		Date[] dates = new Date[2];
		HashMap<String, Date[]> output = new HashMap<String, Date[]>();
		int[] coordinate = searchContent(sheetNum, name);

		if (coordinate[0] != -1 || coordinate[1] != -1) {
			// Name
			nameSemester = readCell(coordinate[0], coordinate[1], sheetNum);

			// Date de début
			dates[0] = readCellDate(coordinate[0], coordinate[1]+1, sheetNum);

			// Date de fin
			dates[1] = readCellDate(coordinate[0], coordinate[1]+2, sheetNum);
			
			calendar.addSemesters(nameSemester, dates[0], dates[1]);
			
		} else {
			throw new NameNotFoundException(name + " : not found");
		}

		return output;
	}

	public void readSemesters(int sheetNum) {
		@SuppressWarnings("unused")
		HashMap<String, Date[]> buffer;
		String[] semesterNames = { "S5", "S6", "S7", "S8", "S9", "S10" };
		this.calendar.clearSemesters();

		for (String name : semesterNames) {
			try {
				buffer = readSemester(sheetNum, name);
			} catch (NameNotFoundException e) {
				e.printStackTrace();
				e.getMessage();
				break;
			}
		}
	}

	/**
	 * @return the calendar
	 */
	public OriginalCalendar getCalendar() {
		return calendar;
	}

}
