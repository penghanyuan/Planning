package com.polytech.planning.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class OriginalCalendar {

	private HashMap<String, Date[]> semesters;
	private HashMap<String, Date[]> holidays;
	private HashMap<String, String[]> freeDays;
	private SimpleDateFormat dateFormatter;

	public OriginalCalendar() {
		this.semesters = new HashMap<String, Date[]>();
		this.holidays = new HashMap<String, Date[]>();
		this.freeDays = new HashMap<String, String[]>();
		this.dateFormatter = new SimpleDateFormat("mm/dd/yy");
	}

	/**
	 * @return the semesters
	 */
	public HashMap<String, Date[]> getSemesters() {
		return semesters;
	}

	/**
	 * @param semsters
	 *            the semesters to set
	 */
	public void addSemesters(String name, Date start, Date end) {
		Date[] dates = new Date[2];
		dates[0] = start;
		dates[1] = end;
		this.semesters.put(name, dates);		
	}
	
	/**
	 * @param semsters
	 *            the semesters to set
	 */
	public void clearSemesters() {
		this.semesters.clear();	
		this.semesters = new HashMap<String, Date[]>();
	}
	
	/**
	 * @param semsters
	 *            the semesters to set
	 */
	public void addSemesters(String name, String start, String end) {
		Date[] dates = new Date[2];
		Date startDate;
		Date endDate;
		
		try {
			startDate = this.dateFormatter.parse(start);
			endDate = this.dateFormatter.parse(start);
			dates[0] = startDate;
			dates[1] = endDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.semesters.put(name, dates);		
	}

	/**
	 * @return the holidays
	 */
	public HashMap<String, Date[]> getHolidays() {
		return holidays;
	}

	/**
	 * @param holidays
	 *            the holidays to set
	 */
	public void addHolidays(String name, String start, String end) {
		Date[] dates = new Date[2];
		Date startDate;
		Date endDate;
		try {
			startDate = this.dateFormatter.parse(start);
			endDate = this.dateFormatter.parse(start);
			dates[0] = startDate;
			dates[1] = endDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		this.holidays.put(name, dates);
	}

	/**
	 * @return the freeDays
	 */
	public HashMap<String, String[]> getFreeDays() {
		return freeDays;
	}

	/**
	 * @param freeDays
	 *            the freeDays to set
	 */
	public void addFreeDays(String name, String date, String creneaux) throws Exception {
		String[] infos = new String[2];
		infos[0] = date;
		infos[1] = creneaux;

		int creneauxNb = Integer.parseInt(creneaux);

		if (creneauxNb < 0 && creneauxNb > 4)
			throw new Exception("creaneaux doit etre compris entre 0 et 4");

		this.freeDays.put(name, infos);
	}

	public String semesterShow() {
		int size = this.semesters.size();
		String retour = "Size = " + size;

		for (String name : semesters.keySet()) {

			String key = name.toString();
			Date dateStart = semesters.get(name)[0];
			Date dateEnd = semesters.get(name)[1];

			retour = retour + "\n" + key + " - " + dateStart + " / " + dateEnd;
		}

		return retour;
	}

	public boolean semestersAreEquals(HashMap<String, Date[]> input) {
		if (this.semesters.size() != input.size()) {
			return false;
		}

		Set<String> keysSemesters = this.semesters.keySet();
		Set<String> keysInput = input.keySet();
		
		Iterator<String> itSemester = keysSemesters.iterator();
		Iterator<String> itInput = keysInput.iterator();

		while (itSemester.hasNext()) {
			
			Object keySemester = itSemester.next();
			Date[] valueSemester = this.semesters.get(keySemester);
			
			Object keyInput = itInput.next();
			Date[] valueInput = input.get(keyInput);
			
			if(valueSemester.length == valueInput.length) {
				for(int i=0 ; i < valueSemester.length ; i++) {
					if(!valueSemester[i].equals(valueInput[i])) {
						return false;
					}
				}
			} else {
				return false;
			}
			
			if(!keySemester.equals(keyInput) && !valueSemester.equals(valueInput)) {
				return false;
			}
		}

		return true;
	}
}
