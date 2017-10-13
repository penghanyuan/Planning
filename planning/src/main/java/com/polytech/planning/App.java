package com.polytech.planning;

import com.polytech.planning.controller.ReadCalendar;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ReadCalendar readCalendar = new ReadCalendar("Calendar.xlsx");
		readCalendar.readSemester(0, 0, 0);
	}
}
