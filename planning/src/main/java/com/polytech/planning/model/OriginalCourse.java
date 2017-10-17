package com.polytech.planning.model;

public class OriginalCourse {
	
	private String courseName;
	
	private Double hoursCM;
	private Double hoursTD;
	private Double hoursTP;
	private Double hoursProject;
	
	private String teachers;
	
	private boolean mundus;

	public OriginalCourse() {
		this.courseName = null;
		this.hoursCM = null;
		this.hoursTD = null;
		this.hoursTP = null;
		this.setHoursProject(null);
		this.teachers = null;
		this.mundus = false;
	}

	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * @return the hoursCM
	 */
	public Double getHoursCM() {
		return hoursCM;
	}

	/**
	 * @param hoursCM the hoursCM to set
	 */
	public void setHoursCM(Double hoursCM) {
		this.hoursCM = hoursCM;
	}

	/**
	 * @return the hoursTD
	 */
	public Double getHoursTD() {
		return hoursTD;
	}

	/**
	 * @param hoursTD the hoursTD to set
	 */
	public void setHoursTD(Double hoursTD) {
		this.hoursTD = hoursTD;
	}

	/**
	 * @return the hoursTP
	 */
	public Double getHoursTP() {
		return hoursTP;
	}

	/**
	 * @param hoursTP the hoursTP to set
	 */
	public void setHoursTP(Double hoursTP) {
		this.hoursTP = hoursTP;
	}
	

	/**
	 * @return the hoursProject
	 */
	public Double getHoursProject() {
		return hoursProject;
	}

	/**
	 * @param hoursProject the hoursProject to set
	 */
	public void setHoursProject(Double hoursProject) {
		this.hoursProject = hoursProject;
	}

	/**
	 * @return the teachers
	 */
	public String getTeachers() {
		return teachers;
	}

	/**
	 * @param teachers the teachers to set
	 */
	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}

	/**
	 * @return the mundus
	 */
	public boolean isMundus() {
		return mundus;
	}

	/**
	 * @param mundus the mundus to set
	 */
	public void setMundus(boolean mundus) {
		this.mundus = mundus;
	}
}
