package com.polytech.planning.model;

import java.util.ArrayList;
import java.util.List;

public class TeachingUnit {
	
	private String name;
	private List<Course> listCourses;
	
	/**
	 * TeachingUnit's Constructor
	 * @param name
	 */
	public TeachingUnit(String name) {
		this.name = name;
		listCourses = new ArrayList<Course>();
	}
}
