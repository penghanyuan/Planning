package com.polytech.planning.model;

public class Teacher {

	private String name;
	
	private int hoursCM;
	private int hoursTD;
	private int hoursTP;
	private int hoursProjet;
	
	private boolean TDMundus;
	private boolean TPMundus;
	
	public Teacher() {
		this.TDMundus = false;
		this.TPMundus = false;
		this.hoursCM = 0;
		this.hoursProjet = 0;
		this.hoursTD = 0;
		this.hoursTP = 0;
	}
	
	/**
	 * Teacher's Constructor
	 * @param lastName
	 * @param firstName
	 */
	public Teacher(String name) {
		this.name = name;
		this.TDMundus = false;
		this.TPMundus = false;
		this.hoursCM = 0;
		this.hoursProjet = 0;
		this.hoursTD = 0;
		this.hoursTP = 0;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the tDMundus
	 */
	public boolean isTDMundus() {
		return TDMundus;
	}

	/**
	 * @param tDMundus the tDMundus to set
	 */
	public void setTDMundus(boolean tDMundus) {
		TDMundus = tDMundus;
	}

	/**
	 * @return the tPMundus
	 */
	public boolean isTPMundus() {
		return TPMundus;
	}

	/**
	 * @param tPMundus the tPMundus to set
	 */
	public void setTPMundus(boolean tPMundus) {
		TPMundus = tPMundus;
	}

	/**
	 * @return the hoursProjet
	 */
	public int getHoursProjet() {
		return hoursProjet;
	}

	/**
	 * @param hoursProjet the hoursProjet to set
	 */
	public void setHoursProjet(int hoursProjet) {
		this.hoursProjet = hoursProjet;
	}
}
