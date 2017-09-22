package com.polytech.planning.model;

import java.util.Date;

public class Holiday {
	private Date startDate;
	private Date endDate;
	
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
}
