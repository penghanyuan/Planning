package com.polytech.planning.controller;

import java.util.Date;
import java.util.HashMap;

import com.polytech.planning.model.OriginalCalendar;

public class ReadCalendar extends ReadFile {

	private OriginalCalendar calendar;

	public ReadCalendar(String filePath) {
		super(filePath);
		calendar = new OriginalCalendar();
	}

	public void readSemester(int sheetNum, int colNum, int lineNum){
		System.out.println(searchContent(sheetNum, "S5")[0]);
		System.out.println(searchContent(sheetNum, "S5")[1]);
	}
	
	public void readSemesters(int sheetNb) {
		int[] coordinate = this.getFirstCellNotEmpty(sheetNb);
		int blank = 0;
		
		int colNum = coordinate[0];
		int lineNum = coordinate[1]+1;
		
		System.out.println(colNum+" - "+lineNum);

		String[] buffer = new String[3];

		while (blank < 1) {
			// if empty
			try {
				if (rowIsEmpty(lineNum, sheetNb))
					blank++;
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int j = 0; j < 3; j++) {
				try {
					if (cellIsEmpty(lineNum, j + colNum, sheetNb)) { // Génère une exception NullPointerException
						System.out.println("toto");
						blank++;
					} else {
						if(blank < 1)
							buffer[j] = readCell(lineNum, j + colNum, sheetNb);
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
					System.out.println("43 - ReadCalendar");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Store semester
			calendar.addSemesters(buffer[0], buffer[1], buffer[2]);

			lineNum++;
		}
	}

	/**
	 * @return the calendar
	 */
	public OriginalCalendar getCalendar() {
		return calendar;
	}

}
