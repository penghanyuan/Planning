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
		String file_name;
		String year;
		String maquettePath;
		String calendarPath;
		String schoolYear;
		String[] schoolYearTable;
		if (command.length < 4) {
			if (command.length == 1 && command[0].equals("-h")) {
				this.help();
			} else {
				throw new Exception(
						"Command error! Please check. You can enter 'java -jar planning-generator.jar -h' to get help.");
			}
		} else {
			schoolYear = command[0];
			year = command[1];
			maquettePath = command[2];
			calendarPath = command[3];
			schoolYearTable = schoolYear.split("/");
			if (year.equals("-di3")) {
				file_name = "Planning Année " + "3 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI3", maquettePath, calendarPath, file_name);
			} else if (year.equals("-di4")) {
				file_name = "Planning Année " + "4 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI4", maquettePath, calendarPath, file_name);
			} else if (year.equals("-di5")) {
				file_name = "Planning Année " + "5 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI5", maquettePath, calendarPath, file_name);
			} else if (year.equals("-all")) {
				file_name = "Planning Année " + "3 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI3", maquettePath, calendarPath, file_name);
				file_name = "Planning Année " + "4 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI4", maquettePath, calendarPath, file_name);
				file_name = "Planning Année " + "5 DI " + schoolYearTable[0] + " - " + schoolYearTable[1] + ".xlsx";
				this.excuteCommand(schoolYear, "DI5", maquettePath, calendarPath, file_name);
			} else {
				throw new Exception("Command error! Please check");
			}

		}
	}
	/**
	 * excute the command
	 * @param school_year
	 * @param annee
	 * @param maquette_path
	 * @param calendar_path
	 * @param file_name
	 */

	private void excuteCommand(String school_year, String annee, String maquette_path, String calendar_path,
			String file_name) {
		WritePlanning wp = null;
		GeneratePlanning gp = null;
		try {
			gp = new GeneratePlanning(school_year, maquette_path, calendar_path);
			wp = new WritePlanning(gp.getPlanningByYear(annee), annee, file_name);
			wp.createFile();
			System.out.println("Creating <" + file_name + "> finished");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * offer help information for user
	 */
	private void help() {
		System.out.println("------------------------------------------------------------");
		System.out.println("The format of command is:");
		System.out.println(
				"java -jar planning-generator.jar <school_year> <-di3|-di4|-di5|-all> <path_of_maquette> <path_of_calendar>");
		System.out.println("------------------------------------------------------------");
		System.out.println("Explication:");
		System.out.println("\t <school_year> : format-> yearStart/yearEnd");
		System.out.println("\t\t Obligatory, the school year of the planning. eg. 2017/2018");
		System.out.println("\t");
		System.out.println("\t <-di3|-di4|-di5|-all> :");
		System.out.println("\t\t Obligatory, the year of the planning you want to generate.");
		System.out.println("\t\t\t -di3: for DI3");
		System.out.println("\t\t\t -di4: for DI4");
		System.out.println("\t\t\t -di5: for DI5");
		System.out.println("\t\t\t -all: for all three years");
		System.out.println("\t");
		System.out.println("\t <path_of_maquette> :");
		System.out.println("\t\t Obligatory, the file path of the maquette");
		System.out.println("\t");
		System.out.println("\t <path_of_calendar> :");
		System.out.println("\t\t Obligatory, the file path of the calendar");
		System.out.println("\t");
		System.out.println("Note: the planning will be genereted at the same place with planning-generator.jar\n"
				+ "The name of the file will be 'Planning Année <year> DI <school_year_start> - <school_year_end>.xlsx'");
		System.out.println("\t");
		System.out.println("\t");
	}

}
