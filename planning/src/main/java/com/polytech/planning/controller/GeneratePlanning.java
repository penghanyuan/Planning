package com.polytech.planning.controller;

import com.polytech.planning.model.Planning;

public class GeneratePlanning {
	private String filePath;
	private String year;
	public GeneratePlanning() {

	}

	public GeneratePlanning(String year, String filePath) {
		this.filePath = filePath;
		this.year = year;
	}

	public Planning setPlanning(){
		ReadCalendar readCalendar = null;
		ReadMockUp readMockUp = null;
		
		readCalendar = new ReadCalendar(this.filePath);
		readMockUp = new ReadMockUp(this.filePath);
		
		ParserMockUp pMockUp = new ParserMockUp();
		ParserCalendar pCalendar = new ParserCalendar();
		Planning planning = new Planning(this.year,pMockUp.createTeachingUnits(),pCalendar.createCalendar());
		
		return null;
	}

}
