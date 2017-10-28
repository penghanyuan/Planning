package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.polytech.planning.model.Course;
import com.polytech.planning.model.OriginalCourse;
import com.polytech.planning.model.Teacher;
import com.polytech.planning.model.TeachingUnit;

public class ParserMockUp {

	private LinkedHashMap<String, List<OriginalCourse>> originalCourses;

	// add calendar

	public ParserMockUp() {
		this.originalCourses = new LinkedHashMap<String, List<OriginalCourse>>();
	}

	/**
	 * @param originalCourses
	 */
	public ParserMockUp(LinkedHashMap<String, List<OriginalCourse>> originalCourses) {
		this.originalCourses = originalCourses;
	}

	/**
	 * 
	 * @return
	 */
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
				c.setTotalCM(oc.getHoursCM());
			}
			if (oc.getHoursTD() != null) {
				c.setTotalTD(oc.getHoursTD());
			}
			if (oc.getHoursTP() != null) {
				c.setTotalTP(oc.getHoursTP());
			}

			if (oc.getTeachers() != null) {
				c.setListTeachers(this.createTeachers(oc.getTeachers()));
			}
			coursesList.add(c);
	//		System.out.println("Courses after parser :" + c.getName());
		}
		if (coursesList.isEmpty())
			return null;
		else
			return coursesList;
	}

	/**
	 * 
	 * @param oriTeachers
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<Teacher> createTeachers(String oriTeachers) {
		List<Teacher> teachers = new ArrayList<Teacher>();

		String[] teachersInits = oriTeachers.split(";");
		LinkedHashMap<String, String[]> teacherAndCourses = new LinkedHashMap<String, String[]>();

		for (int i = 0; i < teachersInits.length; i++) {
			teacherAndCourses.put(teachersInits[i].split(",")[0], teachersInits[i].split(","));
		}

		for (String teacherName : teacherAndCourses.keySet()) {
			// System.out.println(teacherName.trim());
			Teacher teacher = new Teacher(teacherName.trim());

			String[] tempTeacher = teacherAndCourses.get(teacherName);
			for (int i = 1; i < tempTeacher.length; i++) {
				String regCM = ".*CM.*", regTD = ".*TD.*", regTP = ".*TP.*", regMundus = ".*Mundus.*";

				// System.out.println(tempTeacher[i].trim());
				String tempTeacherNoSpace = tempTeacher[i].trim();
				if (tempTeacherNoSpace.matches(regCM)) {
					// CM
					Pattern pattern = Pattern.compile("(\\d*)(hCM)");
					Matcher matcher = pattern.matcher(tempTeacherNoSpace);
					if (matcher.find()) {
						int hoursCM = Integer.parseInt(matcher.group(1));
						// System.out.println(hoursCM);
						teacher.setHoursCM(hoursCM);
					}

				}
				if (tempTeacherNoSpace.matches(regTD)) {
					// TD
					Pattern pattern = Pattern.compile("(\\d*)(hTD)");
					Matcher matcher = pattern.matcher(tempTeacherNoSpace);
					if (matcher.find()) {
						int hoursTD = Integer.parseInt(matcher.group(1));
						// System.out.println(hoursTD);
						teacher.setHoursTD(hoursTD);
					}
					if (tempTeacherNoSpace.matches(".*Mundus.*")) {
						teacher.setTDMundus(true);
					}

				}
				if (tempTeacherNoSpace.matches(regTP)) {
					// TP
					Pattern pattern = Pattern.compile("(\\d*)(hTP)");
					Matcher matcher = pattern.matcher(tempTeacherNoSpace);
					if (matcher.find()) {
						int hoursTP = Integer.parseInt(matcher.group(1));
						// System.out.println(hoursTP);
						teacher.setHoursTP(hoursTP);
					}
					if (tempTeacherNoSpace.matches(".*Mundus.*")) {
						teacher.setTPMundus(true);
					}
				}
			}
			teachers.add(teacher);
		//	System.out.println("Teacher after parser :" + teacher.getName());
		}

		return teachers;
	}

}
