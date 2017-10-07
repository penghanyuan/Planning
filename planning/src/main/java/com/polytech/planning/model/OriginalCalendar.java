package com.polytech.planning.model;

import java.util.HashMap;

public class OriginalCalendar {
	
	private HashMap<String,String[]> semesters;
	private HashMap<String,String[]> holidays;
	private HashMap<String,String[]> freeDays;

	public OriginalCalendar() {
		this.semesters = new HashMap<String,String[]>();
		this.holidays = new HashMap<String,String[]>();
		this.freeDays = new HashMap<String,String[]>();
	}

	/**
	 * @return the semesters
	 */
	public HashMap<String, String[]> getSemesters() {
		return semesters;
	}

	/**
	 * @param semsters the semesters to set
	 */
	public void addSemesters(String name, String start, String end) {
		String[] dates = new String[2];
		dates[0] = start;
		dates[1] = end;
		this.semesters.put(name, dates);
	}

	/**
	 * @return the holidays
	 */
	public HashMap<String, String[]> getHolidays() {
		return holidays;
	}

	/**
	 * @param holidays the holidays to set
	 */
	public void setHolidays(HashMap<String, String[]> holidays) {
		this.holidays = holidays;
	}

	/**
	 * @return the freeDays
	 */
	public HashMap<String, String[]> getFreeDays() {
		return freeDays;
	}

	/**
	 * @param freeDays the freeDays to set
	 */
	public void setFreeDays(HashMap<String, String[]> freeDays) {
		this.freeDays = freeDays;
	}
	
	

}
