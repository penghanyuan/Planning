package com.polytech.planning.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
	
	private String name;
	
	private List<Teacher> listTeachers;
	
	private int totalCM;
	private int totalTD;
	private int totalTP;
	private int totalCT;
	
	/**
	 * Course's Constructor
	 * @param name
	 */
	public Course(String name) {
		listTeachers = new ArrayList<Teacher>();
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
