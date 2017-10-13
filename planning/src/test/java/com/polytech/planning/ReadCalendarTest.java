package com.polytech.planning;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.*;

import com.polytech.planning.controller.ReadCalendar;
import com.polytech.planning.model.OriginalCalendar;

public class ReadCalendarTest{

	private static OriginalCalendar calendar;
	private static ReadCalendar readCalendar;

	@BeforeClass
	public static void init() {
		calendar = new OriginalCalendar();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
		
		String startS5String = "11/09/2017";
		String endS5String = "22/01/2018";
		String endS6String = "04/06/2018";
		String endS10String = "08/04/2018";
		
		Date startS5Date;
		Date endS5Date;
		Date endS6Date;
		Date endS10Date;
		try {
			startS5Date = simpleDateFormat.parse(startS5String);
			endS5Date = simpleDateFormat.parse(endS5String);
			endS6Date = simpleDateFormat.parse(endS6String);
			endS10Date = simpleDateFormat.parse(endS10String);

			calendar.addSemesters("S5", startS5Date, endS5Date);
			calendar.addSemesters("S6", endS5Date, endS6Date);
			calendar.addSemesters("S7", startS5Date, endS5Date);
			calendar.addSemesters("S8", endS5Date, endS6Date);
			calendar.addSemesters("S9", startS5Date, endS5Date);
			calendar.addSemesters("S10", endS5Date, endS10Date);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		readCalendar = new ReadCalendar("Calendar.xlsx");
		readCalendar.readSemesters(0);
	}
	
	@Test
	public void testSearchContent() {
		int[] coordonates1 = readCalendar.searchContent(0, "S8");
		int[] coordonates2 = readCalendar.searchContent(2, "Nuit de l'info");
		int[] coordonates3 = readCalendar.searchContent(0, "Date de fin");
		
		Assert.assertEquals(5, coordonates1[0]); // Ligne
		Assert.assertEquals(2, coordonates1[1]); // Colonne
		
		Assert.assertEquals(4, coordonates2[0]); // Ligne
		Assert.assertEquals(1, coordonates2[1]); // Colonne
		
		Assert.assertEquals(1, coordonates3[0]); // Ligne
		Assert.assertEquals(4, coordonates3[1]); // Colonne
	}

	/*@Test
	public void testReadSemester() {	
		int[] coordonates = readCalendar.getFirstCellNotEmpty(0);
		
		System.out.println("Ligne 1 => "+coordonates[0]);
		System.out.println("Colonne 1 => "+coordonates[1]);
		
		System.out.println(readCalendar.getCalendar().semesterShow());
		System.out.println(calendar.semesterShow());
		
		//Assert.assertTrue(readCalendar.getCalendar().semestersAreEquals(calendar.getSemesters()));
	}*/
	
	@Test
	public void testGetFirstCell() {
		int[] coordonates = readCalendar.getFirstCellNotEmpty(0);
		
		Assert.assertEquals(2, coordonates[0]); // Ligne
		Assert.assertEquals(1, coordonates[1]); // Colonne
	}
}
