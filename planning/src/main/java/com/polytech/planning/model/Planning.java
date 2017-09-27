package com.polytech.planning.model;

import java.util.List;

public class Planning {
	
	private int year;
	
	private List<TeachingUnit> teachingUnits;
	private List<Semester> semesters;
	/**
	 * @param year
	 * @param teachingUnits
	 * @param semesters
	 */
	public Planning(int year, List<TeachingUnit> teachingUnits, List<Semester> semesters) {
		this.year = year;
		this.teachingUnits = teachingUnits;
		this.semesters = semesters;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the teachingUnits
	 */
	public List<TeachingUnit> getTeachingUnits() {
		return teachingUnits;
	}
	/**
	 * @param teachingUnits the teachingUnits to set
	 */
	public void setTeachingUnits(List<TeachingUnit> teachingUnits) {
		this.teachingUnits = teachingUnits;
	}
	/**
	 * @return the semesters
	 */
	public List<Semester> getSemesters() {
		return semesters;
	}
	/**
	 * @param semesters the semesters to set
	 */
	public void setSemesters(List<Semester> semesters) {
		this.semesters = semesters;
	}
	
	
}
