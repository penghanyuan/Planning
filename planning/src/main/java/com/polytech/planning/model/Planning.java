package com.polytech.planning.model;

import java.util.List;

public class Planning {

	private String year;

	private List<TeachingUnit> teachingUnits;
	private Calendar calendar;

	/**
	 * @param year
	 * @param teachingUnits
	 * @param semesters
	 */
	public Planning(String year, List<TeachingUnit> teachingUnits, Calendar calendar) {
		this.year = year;
		this.teachingUnits = teachingUnits;
		this.calendar = calendar;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the teachingUnits
	 */
	public List<TeachingUnit> getTeachingUnits() {
		return teachingUnits;
	}

	/**
	 * @param teachingUnits
	 *            the teachingUnits to set
	 */
	public void setTeachingUnits(List<TeachingUnit> teachingUnits) {
		this.teachingUnits = teachingUnits;
	}

	/**
	 * @return the calendar
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar the calendar to set
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

}
