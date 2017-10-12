package com.polytech.planning;

import org.junit.*;

import com.polytech.planning.controller.ReadCalendar;
import com.polytech.planning.model.OriginalCalendar;

public class ReadCalendarTest{

	private static OriginalCalendar calendar;
	private static ReadCalendar readCalendar;

	@BeforeClass
	public static void init() {
		calendar = new OriginalCalendar();

		calendar.addSemesters("S5", "11/09/2017", "22/01/2018");
		calendar.addSemesters("S6", "22/01/2018", "04/06/2018");
		calendar.addSemesters("S7", "11/09/2017", "22/01/2018");
		calendar.addSemesters("S8", "22/01/2018", "04/06/2018");
		calendar.addSemesters("S9", "11/09/2017", "22/01/2018");
		calendar.addSemesters("S10", "22/01/2018", "08/04/2018");
	}

	@Test
	public void testReadSemester() {
		readCalendar = new ReadCalendar("Calendar.xlsx");
		readCalendar.readSemesters(1);
		
		Assert.assertTrue(readCalendar.getCalendar().semestersAreEquals(calendar.getSemesters()));
	}
}
