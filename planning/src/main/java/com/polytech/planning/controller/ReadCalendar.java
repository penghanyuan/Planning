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
		sheetNb--;
		int i = 2;

		String[] buffer = new String[3];

		while (blank < 1) {
			// if empty
			try {
				if (rowIsEmpty(i, sheetNb))
					blank++;
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int j = 0; j < 3; j++) {
				try {
					if (cellIsEmpty(i, j + columnNb, sheetNb)) {
						new NullPointerException("La cellule ligne " + i + 1 + " et colonne " + j + columnNb + 1 + " est vide.");
						blank++;
					} else {
						if(blank < 1)
							buffer[j] = readCell(i, j + columnNb, sheetNb);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Store semester
			calendar.addSemesters(buffer[0], buffer[1], buffer[2]);

			i++;
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
