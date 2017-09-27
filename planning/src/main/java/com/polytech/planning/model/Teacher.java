package com.polytech.planning.model;

public class Teacher {
	
	private String lastName;
	private String firstName;
	
	private int hoursCM;
	private int hoursTD;
	private int hoursTP;
	private int hoursCT;
	
	/**
	 * Teacher's Constructor
	 * @param lastName
	 * @param firstName
	 */
	public Teacher(String lastName, String firstName) {
		this.lastName = lastName;
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the hoursCM
	 */
	public int getHoursCM() {
		return hoursCM;
	}

	/**
	 * @return the hoursTD
	 */
	public int getHoursTD() {
		return hoursTD;
	}

	/**
	 * @return the hoursTP
	 */
	public int getHoursTP() {
		return hoursTP;
	}

	/**
	 * @return the hoursCT
	 */
	public int getHoursCT() {
		return hoursCT;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param hoursCM the hoursCM to set
	 */
	public void setHoursCM(int hoursCM) {
		this.hoursCM = hoursCM;
	}

	/**
	 * @param hoursTD the hoursTD to set
	 */
	public void setHoursTD(int hoursTD) {
		this.hoursTD = hoursTD;
	}

	/**
	 * @param hoursTP the hoursTP to set
	 */
	public void setHoursTP(int hoursTP) {
		this.hoursTP = hoursTP;
	}

	/**
	 * @param hoursCT the hoursCT to set
	 */
	public void setHoursCT(int hoursCT) {
		this.hoursCT = hoursCT;
	}
}
