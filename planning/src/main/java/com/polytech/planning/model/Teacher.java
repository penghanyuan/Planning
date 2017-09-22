package com.polytech.planning.model;

public class Teacher {
	
	private String lastName;
	private String firstName;
	
	private int hoursCM;
	private int hoursTD;
	private int hoursTP;
	private int hoursCT;
	
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
}
