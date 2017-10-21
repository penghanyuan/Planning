package com.polytech.planning;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.*;

import com.polytech.planning.controller.ParserCalendar;
import com.polytech.planning.model.Calendar;
import com.polytech.planning.model.FreeDay;
import com.polytech.planning.model.Holiday;
import com.polytech.planning.model.OriginalCalendar;
import com.polytech.planning.model.Semester;


public class ParserCalendarTest {

	private static Calendar expected;
	private static OriginalCalendar originalCalendar;

	@BeforeClass
	public static void init() {
		List<Semester> semesters = new ArrayList<Semester>();
		List<Holiday> holidays = new ArrayList<Holiday>();
		List<FreeDay> freedays = new ArrayList<FreeDay>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		
		try {
			// Semesters
			Date startS5Date = dateFormat.parse("11/09/2017");
			Date endS5Date = dateFormat.parse("22/01/2018");
			Date endS6Date = dateFormat.parse("04/06/2018");
			Date endS10Date = dateFormat.parse("08/04/2018");

			// Holidays
			Date holidayStart1 = dateFormat.parse("30/10/2017");
			Date holidayEnd1 = dateFormat.parse("06/11/2017");

			Date holidayStart2 = dateFormat.parse("25/12/2017");
			Date holidayEnd2 = dateFormat.parse("08/01/2018");

			Date holidayStart3 = dateFormat.parse("26/02/2018");
			Date holidayEnd3 = dateFormat.parse("05/03/2018");

			Date holidayStart4 = dateFormat.parse("23/04/2018");
			Date holidayEnd4 = dateFormat.parse("07/05/2018");

			// Free days
			Date freeDay1 = dateFormat.parse("20/09/2017");
			Date freeDay2 = dateFormat.parse("16/11/2017");
			Date freeDay3 = dateFormat.parse("08/12/2017");

			holidays.add(new Holiday("Vacances de la Toussaint", holidayStart1, holidayEnd1));
			holidays.add(new Holiday("Vacances de Noël", holidayStart2, holidayEnd2));
			holidays.add(new Holiday("Vacances d'hiver", holidayStart3, holidayEnd3));
			holidays.add(new Holiday("Vacances de printemps", holidayStart4, holidayEnd4));

			freedays.add(new FreeDay("WE Accueil", freeDay1, 2));
			freedays.add(new FreeDay("Forum entreprise SIP pas cours ce jour", freeDay2, 4));
			freedays.add(new FreeDay("Nuit de l'info", freeDay3, 2));

			semesters.add(new Semester("S5", startS5Date, endS5Date, freedays, holidays));
			semesters.add(new Semester("S6", startS5Date, endS6Date, freedays, holidays));
			// semesters.add(new Semester("S7", startS5Date,
			// endS5Date,freedays,holidays));
			// semesters.add(new Semester("S8", startS5Date,
			// endS6Date,freedays,holidays));
			// semesters.add(new Semester("S9", startS5Date,
			// endS5Date,freedays,holidays));
			// semesters.add(new Semester("S10", startS5Date,
			// endS10Date,freedays,holidays));
			
			expected = new Calendar();
			expected.setListSemester(semesters);
			expected.setName("DI3");
			
			originalCalendar = new OriginalCalendar();
			// Add semesters
			originalCalendar.addSemesters("S5", startS5Date, endS5Date);
			originalCalendar.addSemesters("S6", endS5Date, endS6Date);
			originalCalendar.addSemesters("S7", startS5Date, endS5Date);
			originalCalendar.addSemesters("S8", endS5Date, endS6Date);
			originalCalendar.addSemesters("S9", startS5Date, endS5Date);
			originalCalendar.addSemesters("S10", endS5Date, endS10Date);

			// Add holidays
			originalCalendar.addHolidays("Vacances de la Toussaint", holidayStart1, holidayEnd1);
			originalCalendar.addHolidays("Vacances de Noël", holidayStart2, holidayEnd2);
			originalCalendar.addHolidays("Vacances d'hiver", holidayStart3, holidayEnd3);
			originalCalendar.addHolidays("Vacances de printemps", holidayStart4, holidayEnd4);

			// Add free days
			originalCalendar.addFreeDays("WE Accueil", freeDay1, "2");
			originalCalendar.addFreeDays("Forum entreprise SIP pas cours ce jour", freeDay2, "4");
			originalCalendar.addFreeDays("Nuit de l'info", freeDay3, "2");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testParserCalendar() {
		ParserCalendar pc = new ParserCalendar(this.originalCalendar);
		Calendar get = pc.createCalendar();
		Assert.assertEquals("Name equals",expected.getListSemester().get(0).getName(),get.getListSemester().get(0).getName());
	}

}
