package com.polytech.planning;

import com.polytech.planning.controller.ReadCalendar;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ReadCalendar readEmptyCalendar = new ReadCalendar("CalendarEmpty.xlsx");
		//readEmptyCalendar.readSemesters(1);
		
		readEmptyCalendar.getFirstCellNotEmpty(1);
		System.out.println(readEmptyCalendar.getFirstCellNotEmpty(1)[0]);
		System.out.println(readEmptyCalendar.getFirstCellNotEmpty(1)[1]);
	}
}
