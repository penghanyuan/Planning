package com.polytech.planning;

import java.util.List;

import com.polytech.planning.controller.Parser;
import com.polytech.planning.controller.ReadCalendar;
import com.polytech.planning.model.Teacher;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ParserTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ParserTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ParserTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public void testCreateTeachers(){
    	Parser p = new Parser();
    	List<Teacher> ts = p.createTeachers(" ");
    	
    }
    
    public void testCreateCalendar(){
    	ReadCalendar readCalendar = new ReadCalendar("/Users/penghanyuan/Desktop/Calendar.xlsx");
    	
    	try {
    		readCalendar.readSemesters(1);
			readCalendar.readHolidays(2);
			readCalendar.readFreeDays(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	Parser p = new Parser(readCalendar.getCalendar());
    	System.out.println(p.createCalendar().getListSemester());
    }
}
