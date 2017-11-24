package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

	private LinkedHashMap<String, List<Planning>> setPlannings() {
		LinkedHashMap<String, List<Planning>> plannings = new LinkedHashMap<String, List<Planning>>();
		ReadCalendar readCalendar = new ReadCalendar(this.filePathCalender);
		ReadMockUp readMockUp = null;
		List<Planning> planningDI3 = new ArrayList<Planning>();
		List<Planning> planningDI4 = new ArrayList<Planning>();
		List<Planning> planningDI5 = new ArrayList<Planning>();

		readCalendar.readSemesters(0);
		readCalendar.readHolidays(1);
		readCalendar.readFreeDays(2);

		// DI3 S5
		readMockUp = new ReadMockUp(this.filePathMockUp, 1);
		readMockUp.readTeachingUnits();
		ParserMockUp pMockUp = new ParserMockUp(readMockUp.getTeachingUnits());
		ParserCalendar pCalendar = new ParserCalendar(readCalendar.getCalendar());
		Planning planning = new Planning(this.year, pMockUp.createTeachingUnits(), pCalendar.createCalendar());
		planning.getCalendar().setName("S5");
		planningDI3.add(planning);

		// DI3 S6
		readMockUp = new ReadMockUp(this.filePathMockUp, 2);
		readMockUp.readTeachingUnits();
		pMockUp = new ParserMockUp(readMockUp.getTeachingUnits());
		planning = new Planning(this.year, pMockUp.createTeachingUnits(), pCalendar.createCalendar());
		planning.getCalendar().setName("S6");

		planningDI3.add(planning);
		plannings.put("DI3", planningDI3);

		// DI4 S7
		readMockUp = new ReadMockUp(this.filePathMockUp, 3);
		readMockUp.readTeachingUnits();
		pMockUp = new ParserMockUp(readMockUp.getTeachingUnits());
		planning = new Planning(this.year, pMockUp.createTeachingUnits(), pCalendar.createCalendar());
		planning.getCalendar().setName("S7");
		planningDI4.add(planning);

		// DI4 S8
		readMockUp = new ReadMockUp(this.filePathMockUp, 4);
		readMockUp.readTeachingUnits();
		pMockUp = new ParserMockUp(readMockUp.getTeachingUnits());
		planning = new Planning(this.year, pMockUp.createTeachingUnits(), pCalendar.createCalendar());
		planning.getCalendar().setName("S8");
		planningDI4.add(planning);

		plannings.put("DI4", planningDI4);

		// DI5 S9
		readMockUp = new ReadMockUp(this.filePathMockUp, 5);
		readMockUp.readTeachingUnits();
		pMockUp = new ParserMockUp(readMockUp.getTeachingUnits());
		planning = new Planning(this.year, pMockUp.createTeachingUnits(), pCalendar.createCalendar());
		planning.getCalendar().setName("S9");
		planningDI5.add(planning);

		// DI5 S10
		readMockUp = new ReadMockUp(this.filePathMockUp, 6);
		readMockUp.readTeachingUnits();
		pMockUp = new ParserMockUp(readMockUp.getTeachingUnits());
		planning = new Planning(this.year, pMockUp.createTeachingUnits(), pCalendar.createCalendar());
		planning.getCalendar().setName("S10");
		planningDI5.add(planning);

		plannings.put("DI5", planningDI5);
		return plannings;
	}

	public List<Planning> getPlanningByYear(String year) {
		LinkedHashMap<String, List<Planning>> plannings = this.setPlannings();
		return plannings.get(year);
	}

}
