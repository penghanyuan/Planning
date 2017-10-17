package com.polytech.planning.model;

import java.util.ArrayList;
import java.util.List;

public class OriginalTeachingUnit {
	String name;
	List<OriginalCourse> courses;

	public OriginalTeachingUnit() {
		courses = new ArrayList<OriginalCourse>();
	}

	/**
	 * @param input
	 * @return true if add is ok false else
	 */
	public boolean addCourse(OriginalCourse input) {
		return courses.add(input);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the courses
	 */
	public List<OriginalCourse> getCourses() {
		return courses;
	}

	/**
	 * @param courses
	 *            the courses to set
	 */
	public void setCourses(List<OriginalCourse> courses) {
		this.courses = courses;
	}
}
