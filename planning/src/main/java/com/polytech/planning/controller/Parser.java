package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.polytech.planning.model.Course;
import com.polytech.planning.model.OriginalCourse;
import com.polytech.planning.model.Teacher;
import com.polytech.planning.model.TeachingUnit;

public class Parser {

	private HashMap<String, List<OriginalCourse>> originalCourses;
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
		for (int i = 0; i < courses.size(); i++) {
			Course c = new Course(courses.get(i).getCourseName());
			OriginalCourse oc = courses.get(i);
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
		oriTeachers = "C.Tacquard 16hCM, 20hTD x3gr + Mundus, 16hTP x3gr + Mundus  ; M. Martineau 12hTP x3gr + Mundus";
		String sTemp = oriTeachers.replace(",", "/");
		sTemp = sTemp.replace(";", "/");
		sTemp = sTemp.replace(". ", ".");
		sTemp = sTemp.replace(" ", "/");
		sTemp = sTemp.replace("+", "/");
		//sTemp = sTemp.replace("", "/");
		String[] teacherInit = sTemp.split("/");

		for (int i = 0; i < teacherInit.length; i++) {
			teacherInit[i] = teacherInit[i].trim();// delete space
			if (teacherInit[i].compareTo("") != 0)
				System.out.println(teacherInit[i]);
		}
		System.out.println(sTemp);
		return null;
	}
}
