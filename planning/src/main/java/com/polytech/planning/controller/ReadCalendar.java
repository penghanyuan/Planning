package com.polytech.planning.controller;

import com.polytech.planning.model.OriginalCalendar;

public class ReadCalendar extends ReadFile {
	
	private OriginalCalendar calendar;

	public ReadCalendar(String filePath) {
		super(filePath);
		calendar = new OriginalCalendar();
	}
	
	public void readSemesters(int sheetNb) {
		int blank = 0;
		int columnNb = 2;
		int i = 3;
		String[] buffer = new String[3];
		
		while(blank < 1) {
			// if empty
			if(rowIsEmpty(i, sheetNb))
				blank++;
						
			for(int j = 0; j < 3; j++)
				buffer[j] = readCell(i, j+columnNb,  sheetNb);
			
			// Store semester
			calendar.addSemesters(buffer[0],buffer[1],buffer[2]);
			
			i++;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}	
}
