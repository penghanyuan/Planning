package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.List;

public class Course {
	
	private String Name;
	
	private List<Teacher> listTeachers;
	
	private int totalCM;
	private int totalTD;
	private int totalTP;
	private int totalCT;
	
	public Course() {
		listTeachers = new ArrayList<Teacher>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}

	/**
	 * @return the totalCM
	 */
	public int getTotalCM() {
		return totalCM;
	}

	/**
	 * @return the totalTD
	 */
	public int getTotalTD() {
		return totalTD;
	}

	/**
	 * @return the totalTP
	 */
	public int getTotalTP() {
		return totalTP;
	}

	/**
	 * @return the totalCT
	 */
	public int getTotalCT() {
		return totalCT;
	}
	
}
