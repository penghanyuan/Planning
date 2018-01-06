package com.ploytech.planning.view;

import com.polytech.planning.controller.GeneratePlanning;
import com.polytech.planning.controller.WritePlanning;

public class MainView {
	private String[] command;

	/**
	 * @param command
	 */
	public MainView(String[] command) {
		this.command = command;
	}

	/**
	 * planning-generator <school_year> <-di3|-di4|-di5|-all> <path_of_maquette>
	 * <path_of_calendar>
	 * 
	 * @throws Exception
	 */
	public void parseCommand() throws Exception {
		String file_name = "Planning";
		String year;
		String maquettePath;
		String calendarPath;
		String schoolYear;
		String[] schoolYearTable;
		if (command.length < 4) {
			throw new Exception("Command error! Please check");
		} else {
			schoolYear = command[0];
			year = command[1];
			maquettePath = command[2];
			calendarPath = command[3];
			schoolYearTable = schoolYear.split("/");
			if (year.equals("-di3")) {
				file_name += " Année " + "3 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI3", maquettePath, calendarPath, file_name);
			} else if (year.equals("-di4")) {
				file_name += " Année " + "4 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI4", maquettePath, calendarPath, file_name);
			} else if (year.equals("-di5")) {
				file_name += " Année " + "5 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI5", maquettePath, calendarPath, file_name);
			} else if (year.equals("-all")) {
				file_name += " Année " + "3 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI3", maquettePath, calendarPath, file_name);
				file_name += " Année " + "4 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI4", maquettePath, calendarPath, file_name);
				file_name += " Année " + "5 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI5", maquettePath, calendarPath, file_name);
			} else {
				throw new Exception("Command error! Please check");
			}

		}
	}

	private void excuteCommand(String school_year, String annee, String maquette_path, String calendar_path,
			String file_name) {
		WritePlanning wp = null;
		GeneratePlanning gp = null;
		try {
			gp = new GeneratePlanning(school_year, maquette_path, calendar_path);
			wp = new WritePlanning(gp.getPlanningByYear(annee), annee, file_name);
			wp.createFile();
			System.out.println("Finished!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
