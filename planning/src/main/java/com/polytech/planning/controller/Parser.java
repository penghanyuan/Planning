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

	private HashMap<String,List<OriginalCourse>> originalCourses;
	// add calendar

	public Parser() {
		this.originalCourses = null;
	}

	/**
	 * @param originalCourses
	 */
	public Parser(HashMap<String,List<OriginalCourse>> originalCourses) {
		this.originalCourses = originalCourses;
	}

	public List<TeachingUnit> createTeachingUnits() {
		List<TeachingUnit> teachingUnits = new ArrayList<TeachingUnit>();
		
		/*
		 * create list of teaching unit
		 */
		for (String key : originalCourses.keySet()) {  
			TeachingUnit teachingUnit = new TeachingUnit(key);
			teachingUnit.setListCourses(createCourses(originalCourses.get(key)));
			teachingUnits.add(teachingUnit);
		}

		return teachingUnits;
	}

	private List<Course> createCourses(List<OriginalCourse> courses) {
		return null;
	}

	private List<Teacher> createTeachers() {
		return null;
	}
}
