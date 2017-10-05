package com.polytech.planning.model;

import java.util.HashMap;

public class OriginalCalendar {
	
	private String[] semstersStartDate;
	private String[] semstersEndDate;
	private HashMap<String,String[]> holidays;
	private HashMap<String,String[]> freeDays;

	public OriginalCalendar() {
		this.semstersStartDate = null;
		this.semstersEndDate = null;
		this.holidays = new HashMap<String,String[]>();
		this.freeDays = new HashMap<String,String[]>();
	}

	/**
	 * @return the semstersStartDate
	 */
	public String[] getSemstersStartDate() {
		return semstersStartDate;
	}

	/**
	 * @param semstersStartDate the semstersStartDate to set
	 */
	public void setSemstersStartDate(String[] semstersStartDate) {
		this.semstersStartDate = semstersStartDate;
	}

	/**
	 * @return the semstersEndDate
	 */
	public String[] getSemstersEndDate() {
		return semstersEndDate;
	}

	/**
	 * @param semstersEndDate the semstersEndDate to set
	 */
	public void setSemstersEndDate(String[] semstersEndDate) {
		this.semstersEndDate = semstersEndDate;
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
