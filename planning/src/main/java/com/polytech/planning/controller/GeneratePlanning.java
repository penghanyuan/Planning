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
		
		ParserMockUp p = new ParserMockUp();
		Planning planning = new Planning(this.year,null,null);
		
		return null;
	}

}
