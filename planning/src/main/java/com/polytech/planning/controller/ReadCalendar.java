package com.polytech.planning.controller;

import com.polytech.planning.model.OriginalCalendar;

public class ReadCalendar extends ReadFile {

	private OriginalCalendar calendar;

	public ReadCalendar(String filePath) {
		super(filePath);
		calendar = new OriginalCalendar();
	}

	public void readSemesters(int sheetNb) {
		int[] coordinate = this.getFirstCellNotEmpty(sheetNb);
		int blank = 0;
		
		int colNum = coordinate[0];
		int lineNum = coordinate[1]-1;

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

	public void readHolidays(int sheetNb) throws Exception {
		int blank = 0;
		int columnNb = 1;
		int i = 3;

		String[] buffer = new String[3];

		while (blank < 1) {

			for (int j = 0; j < 3; j++) {
				try {
					if (cellIsEmpty(i, j + columnNb, sheetNb)) {
						new Exception("La cellule ligne " + i + 1 + " et colonne " + j + columnNb + 1 + " est vide.");
					} else {
						buffer[j] = readCell(i, j + columnNb, sheetNb);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Store Holiday
			calendar.addHolidays(buffer[0], buffer[1], buffer[2]);

			i++;
		}
	}

	public void readFreeDays(int sheetNb) {
		int blank = 0;
		int columnNb = 1;
		int i = 3;

		String[] buffer = new String[3];
		while (blank < 1) {
			for (int j = 0; j < 3; j++) {
				try {
					if (cellIsEmpty(i, j + columnNb, sheetNb)) {
						new Exception("La cellule ligne " + i + 1 + " et colonne " + j + columnNb + 1 + " est vide.");
					} else {
						buffer[j] = readCell(i, j + columnNb, sheetNb);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Store free days
			try {
				calendar.addFreeDays(buffer[0], buffer[1], buffer[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}

			i++;
		}
	}

	/**
	 * @return the calendar
	 */
	public OriginalCalendar getCalendar() {
		return calendar;
	}

}
