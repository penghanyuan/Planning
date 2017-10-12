package com.polytech.planning.model;

import java.util.HashMap;

public class OriginalCalendar {
	
	private HashMap<String,String[]> semesters;
	private HashMap<String,String[]> holidays;
	private HashMap<String,String[]> freeDays;

	public OriginalCalendar() {
		this.semesters = new HashMap<String,String[]>();
		this.holidays = new HashMap<String,String[]>();
		this.freeDays = new HashMap<String,String[]>();
	}

	/**
	 * @return the semesters
	 */
	public HashMap<String, String[]> getSemesters() {
		return semesters;
	}

	/**
	 * @param semsters the semesters to set
	 */
	public void addSemesters(String name, String start, String end) {
		String[] dates = new String[2];
		dates[0] = start;
		dates[1] = end;
		this.semesters.put(name, dates);
	}

	/**
	 * @return the holidays
	 */
	public HashMap<String, String[]> getHolidays() {
		return holidays;
	}

	/**
	 * @param holidays the holidays to set
	 */
	public void addHolidays(String name, String start, String end) {
		String[] dates = new String[2];
		dates[0] = start;
		dates[1] = end;
		this.holidays.put(name, dates);
	}

	/**
	 * @return the freeDays
	 */
	public HashMap<String, String[]> getFreeDays() {
		return freeDays;
	}

	/**
	 * @param freeDays the freeDays to set
	 */
	public void addFreeDays(String name, String date, String creneaux) throws Exception {
		String[] infos = new String[2];
		infos[0] = date;
		infos[1] = creneaux;
		
		int creneauxNb = Integer.parseInt(creneaux);
		
		if(creneauxNb < 0 && creneauxNb > 4)
			throw new Exception("creaneaux doit etre compris entre 0 et 4");
		
		this.holidays.put(name, infos);
	}
	
	public String semesterShow() {
		int size = this.semesters.size();
		String retour = "Size = "+size+"\n";
		
		for (String name: semesters.keySet()){

            String key =name.toString();
            String dateStart = semesters.get(name)[0].toString();
            String dateEnd = semesters.get(name)[1].toString();

            retour = retour+"\n"+key + " - " + dateStart+" / "+dateEnd;
		}
		
		return retour;
	}
	
	
}
