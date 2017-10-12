package com.polytech.planning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.polytech.planning.controller.ReadCalendar;
import com.polytech.planning.model.OriginalCalendar;

import junit.framework.TestCase;

public class ReadCalendarTest extends TestCase {
	
	private OriginalCalendar calendar;
	private ReadCalendar readCalendar;
	
	@BeforeClass
	public void init() {
		this.calendar = new OriginalCalendar();
		
		this.calendar.addSemesters("S5", "11/09/2017", "22/01/2018");
		this.calendar.addSemesters("S6", "22/01/2018", "04/06/2018");
		this.calendar.addSemesters("S7", "11/09/2017", "22/01/2018");
		this.calendar.addSemesters("S8", "22/01/2018", "04/06/2018");
		this.calendar.addSemesters("S9", "11/09/2017", "22/01/2018");
		this.calendar.addSemesters("S10", "22/01/2018", "08/04/2018");
	}
	
	@Test
	public void testReadSemester(){
    	this.readCalendar = new ReadCalendar("Calendar.xlsx");
    	this.readCalendar.readSemesters(1);
    	
    	//String semesterRead = this.readCalendar.getCalendar().semesterShow();
    	String semester = this.calendar.semesterShow();
    	System.out.println(semester);
    	
    	//Assert.assertTrue(semesterRead.equals(semester));
    }
	
}
