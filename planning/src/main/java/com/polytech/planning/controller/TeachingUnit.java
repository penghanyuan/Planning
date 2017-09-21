package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.List;

public class TeachingUnit {
	
	private String Name;
	private List<Course> listCourses;
	
	public TeachingUnit() {
		listCourses = new ArrayList<Course>();
	}
}
