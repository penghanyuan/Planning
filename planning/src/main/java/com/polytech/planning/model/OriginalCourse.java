package com.polytech.planning.model;

public class OriginalCourse {
	
	private String courseName;
	private String hoursCM;
	private String hoursTD;
	private String hoursTP;
	private String hoursCC;
	private String hoursCT;
	private String teachers;
	
	private boolean mundus;

	public OriginalCourse() {
		this.courseName = null;
		this.hoursCM = null;
		this.hoursTD = null;
		this.hoursTP = null;
		this.hoursCC = null;
		this.hoursCT = null;
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
	public String getHoursCM() {
		return hoursCM;
	}

	/**
	 * @param hoursCM the hoursCM to set
	 */
	public void setHoursCM(String hoursCM) {
		this.hoursCM = hoursCM;
	}

	/**
	 * @return the hoursTD
	 */
	public String getHoursTD() {
		return hoursTD;
	}

	/**
	 * @param hoursTD the hoursTD to set
	 */
	public void setHoursTD(String hoursTD) {
		this.hoursTD = hoursTD;
	}

	/**
	 * @return the hoursTP
	 */
	public String getHoursTP() {
		return hoursTP;
	}

	/**
	 * @param hoursTP the hoursTP to set
	 */
	public void setHoursTP(String hoursTP) {
		this.hoursTP = hoursTP;
	}

	/**
	 * @return the hoursCC
	 */
	public String getHoursCC() {
		return hoursCC;
	}

	/**
	 * @param hoursCC the hoursCC to set
	 */
	public void setHoursCC(String hoursCC) {
		this.hoursCC = hoursCC;
	}

	/**
	 * @return the hoursCT
	 */
	public String getHoursCT() {
		return hoursCT;
	}

	/**
	 * @param hoursCT the hoursCT to set
	 */
	public void setHoursCT(String hoursCT) {
		this.hoursCT = hoursCT;
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
