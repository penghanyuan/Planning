package com.polytech.planning.model;

import java.util.Date;

public class Holiday {
	private String name;
	private Date startDate;
	private Date endDate;
	
	/**
	 * Holiday's Constructor
	 * @param startDate
	 * @param endDate
	 */
	public Holiday(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
