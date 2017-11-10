package com.polytech.planning.controller;

import com.polytech.planning.model.Planning;

public class GeneratePlanning {
	private String filePathMockUp;
	private String filePathCalender;
	private String year;

	public GeneratePlanning() {

	}

	public GeneratePlanning(String year, String filePathMockUp, String filePathCalender) {
		this.filePathMockUp = filePathMockUp;
		this.filePathCalender = filePathCalender;
		this.year = year;
	}

	public Planning setPlanning() {
		ReadCalendar readCalendar = null;
		ReadMockUp readMockUp = null;

		readCalendar = new ReadCalendar(this.filePathCalender);
		readCalendar.readSemesters(0);
		readCalendar.readHolidays(1);
		readCalendar.readFreeDays(2);

		readMockUp = new ReadMockUp(this.filePathMockUp, 1);
		readMockUp.readTeachingUnits();
		ParserMockUp pMockUp = new ParserMockUp(readMockUp.getTeachingUnits());
		ParserCalendar pCalendar = new ParserCalendar(readCalendar.getCalendar());
		Planning planning = new Planning(this.year, pMockUp.createTeachingUnits(), pCalendar.createCalendar());

		return planning;
	}

}
