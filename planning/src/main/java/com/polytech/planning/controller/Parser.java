package com.polytech.planning.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.polytech.planning.model.Calendar;
import com.polytech.planning.model.Course;
import com.polytech.planning.model.FreeDay;
import com.polytech.planning.model.Holiday;
import com.polytech.planning.model.OriginalCalendar;
import com.polytech.planning.model.OriginalCourse;
import com.polytech.planning.model.Semester;
import com.polytech.planning.model.Teacher;
import com.polytech.planning.model.TeachingUnit;

public class Parser {

	private HashMap<String, List<OriginalCourse>> originalCourses;
	private OriginalCalendar originalCalendar;
	// add calendar

	public Parser() {
		this.originalCourses = null;
	}

	/**
	 * @param originalCourses
	 */
	public Parser(HashMap<String, List<OriginalCourse>> originalCourses) {
		this.originalCourses = originalCourses;
	}

	/**
	 * @param originalCourses
	 */
	public Parser(OriginalCalendar originalCalendar) {
		this.originalCalendar = originalCalendar;
	}

	/**
	 * @param originalCourses
	 * @param originalCalendar
	 */
	public Parser(HashMap<String, List<OriginalCourse>> originalCourses, OriginalCalendar originalCalendar) {
		super();
		this.originalCourses = originalCourses;
		this.originalCalendar = originalCalendar;
	}

	public List<TeachingUnit> createTeachingUnits() {
		List<TeachingUnit> teachingUnits = new ArrayList<TeachingUnit>();

		/*
		 * create list of teaching unit
		 */
		for (String key : originalCourses.keySet()) {
			TeachingUnit teachingUnit = new TeachingUnit(key);
			teachingUnit.setListCourses(this.createCourses(originalCourses.get(key)));
			teachingUnits.add(teachingUnit);
		}

		return teachingUnits;
	}

	/**
	 * 
	 * @param courses
	 * @return list of course objects
	 */
	private List<Course> createCourses(List<OriginalCourse> courses) {
		List<Course> coursesList = new ArrayList<Course>();
		for (OriginalCourse oc : courses) {
			Course c = new Course(oc.getCourseName());
			if (oc.getHoursCM() != null) {
				c.setTotalCM(Integer.parseInt(oc.getHoursCM()));
			}
			if (oc.getHoursTD() != null) {
				c.setTotalTD(Integer.parseInt(oc.getHoursTD()));
			}
			if (oc.getHoursTP() != null) {
				c.setTotalTP(Integer.parseInt(oc.getHoursTP()));
			}
			// il manque hoursCC & mundus
			if (oc.getHoursCT() != null) {
				c.setTotalCT(Integer.parseInt(oc.getHoursCT()));
			}
			if (oc.getTeachers() != null) {
				c.setListTeachers(this.createTeachers(oc.getTeachers()));
			}
			coursesList.add(c);
		}
		if (coursesList.isEmpty())
			return null;
		else
			return coursesList;
	}

	public List<Teacher> createTeachers(String oriTeachers) {
		List<Teacher> teachers = new ArrayList<Teacher>();
		oriTeachers = "C.Tacquard,16hCM, 20hTD x3gr + Mundus, 16hTP x3gr + Mundus; M. Martineau,12hTP x3gr + Mundus";
		String[] teachersInits = oriTeachers.split(";");
		HashMap<String, String[]> teacherAndCourses = new HashMap<String, String[]>();

		for (int i = 0; i < teachersInits.length; i++) {
			teacherAndCourses.put(teachersInits[i].split(",")[0], teachersInits[i].split(","));
		}

		for (String teacherName : teacherAndCourses.keySet()) {
			System.out.println(teacherName.trim());
			Teacher teacher = new Teacher(teacherName.trim());

			String[] tempTeacher = teacherAndCourses.get(teacherName);
			for (int i = 1; i < tempTeacher.length; i++) {
				String regCM = ".*CM.*", regTD = ".*TD.*", regTP = ".*TP.*", regMundus = ".*Mundus.*";
				
				System.out.println(tempTeacher[i].trim());
				String tempTeacherNoSpace = tempTeacher[i].trim();
				if (tempTeacherNoSpace.matches(regCM)) {
					// CM
					Pattern pattern = Pattern.compile("(\\d*)(hCM)");
					Matcher matcher = pattern.matcher(tempTeacherNoSpace);
					if(matcher.find()){
						int hoursCM = Integer.parseInt(matcher.group(1));
						//System.out.println(hoursCM);
						teacher.setHoursCM(hoursCM);
					}
					
				}
				if (tempTeacherNoSpace.matches(regTD)) {
					// TD
					Pattern pattern = Pattern.compile("(\\d*)(hTD)");
					Matcher matcher = pattern.matcher(tempTeacherNoSpace);
					if(matcher.find()){
						int hoursTD = Integer.parseInt(matcher.group(1));
						//System.out.println(hoursTD);
						teacher.setHoursTD(hoursTD);
					}
					if(tempTeacherNoSpace.matches(".*Mundus.*")){
						teacher.setTDMundus(true);
					}
					
				}
				if (tempTeacherNoSpace.matches(regTP)) {
					//TP
					Pattern pattern = Pattern.compile("(\\d*)(hTP)");
					Matcher matcher = pattern.matcher(tempTeacherNoSpace);
					if(matcher.find()){
						int hoursTP = Integer.parseInt(matcher.group(1));
						//System.out.println(hoursTP);
						teacher.setHoursTP(hoursTP);
					}
					if(tempTeacherNoSpace.matches(".*Mundus.*")){
						teacher.setTPMundus(true);
					}
				}
			}
			teachers.add(teacher);
		}
		
		return teachers;
	}

	public Calendar createCalendar() {
		Calendar calendar = new Calendar();
		calendar.setListSemester(this.createSemester());
		return calendar;
	}

	private List<Semester> createSemester() {
		List<Semester> semesters = new ArrayList<Semester>();

		for (String key : this.originalCalendar.getSemesters().keySet()) {

			Semester semester;

			DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
			Date sdate, edate;
			sdate = this.originalCalendar.getSemesters().get(key)[0];
			edate = this.originalCalendar.getSemesters().get(key)[1];
			//sdate = format.parse(this.originalCalendar.getSemesters().get(key)[0]);
			//edate = format.parse(this.originalCalendar.getSemesters().get(key)[1]);
			semester = new Semester(key);
			semester.setStartDate(sdate);
			semester.setEndDate(edate);
			
			semester.setListHoliday(this.createHolidays());
			semester.setListFreeDays(this.createFreeDays());
			semesters.add(semester);

		}
		return semesters;
	}

	private List<FreeDay> createFreeDays() {
		List<FreeDay> freeDays = new ArrayList<FreeDay>();
		for (Date date : this.originalCalendar.getFreeDays().keySet()) {
			FreeDay freeDay;
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
			String name;
			
			name = this.originalCalendar.getFreeDays().get(date)[0];
			
			freeDay = new FreeDay();
			freeDay.setName(name);
			freeDay.setDate(date);
			
			int timeSlot = Integer.parseInt(this.originalCalendar.getFreeDays().get(date)[1]);
			freeDay.setTimeslot(timeSlot);

			freeDays.add(freeDay);
		}
		return freeDays;
	}

	private List<Holiday> createHolidays() {
		List<Holiday> holidays = new ArrayList<Holiday>();

		for (String name : this.originalCalendar.getHolidays().keySet()) {
			Holiday holiday;
			//DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
			
			Date sdate, edate;
			//System.out.println(originalCalendar.getHolidays().get(name));
			sdate = this.originalCalendar.getHolidays().get(name)[0];
			edate = this.originalCalendar.getHolidays().get(name)[1];
			
			//sdate = format.parse(this.originalCalendar.getHolidays().get(name)[0]);
			//edate = format.parse(this.originalCalendar.getHolidays().get(name)[1]);
			
			holiday = new Holiday(name, sdate, edate);
			
			holidays.add(holiday);
		}
		return holidays;
	}
}
