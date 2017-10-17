package com.polytech.planning.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
	
	private String name;
	
	private List<Teacher> listTeachers;
	
	private int totalCM;
	private int totalTD;
	private int totalTP;
	private int totalProject;
	
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
	 * @return the listTeachers
	 */
	public List<Teacher> getListTeachers() {
		return listTeachers;
	}

	/**
	 * @param listTeachers the listTeachers to set
	 */
	public void setListTeachers(List<Teacher> listTeachers) {
		this.listTeachers = listTeachers;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param totalCM the totalCM to set
	 */
	public void setTotalCM(int totalCM) {
		this.totalCM = totalCM;
	}

	/**
	 * @param totalTD the totalTD to set
	 */
	public void setTotalTD(int totalTD) {
		this.totalTD = totalTD;
	}

	/**
	 * @param totalTP the totalTP to set
	 */
	public void setTotalTP(int totalTP) {
		this.totalTP = totalTP;
	}

	/**
	 * @return the totalProject
	 */
	public int getTotalProject() {
		return totalProject;
	}

	/**
	 * @param totalProject the totalProject to set
	 */
	public void setTotalProject(int totalProject) {
		this.totalProject = totalProject;
	}	
}
