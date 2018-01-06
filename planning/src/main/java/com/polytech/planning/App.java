package com.polytech.planning;

import com.polytech.planning.controller.GeneratePlanning;
import com.polytech.planning.controller.WritePlanning;

public class App {
	public static void main(String[] args) {
		WritePlanning wp = null;
		GeneratePlanning gp = new GeneratePlanning("2017/2018", "Maquette.xlsx", "Calendar.xlsx");
		try {
			wp = new WritePlanning(gp.getPlanningByYear("DI3"), "DI3", "Planning Année 3 DI 2017-2018.xlsx");
			wp.createFile();
		} catch (Exception e) {
			System.out.println(e.getMessage());;
		}
		
	}
}
