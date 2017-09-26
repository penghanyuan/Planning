package com.polytech.planning.model;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
	private List<Semester> listSemester;

	/**
	 * Calendar's Constructor
	 */
	public Calendar() {
		listSemester = new ArrayList<Semester>();
	}

	/**
	 * @return the listSemester
	 */
	public List<Semester> getListSemester() {
		return listSemester;
	}

	/**
	 * @param listSemester the listSemester to set
	 */
	public void setListSemester(List<Semester> listSemester) {
		this.listSemester = listSemester;
	}
	
}
