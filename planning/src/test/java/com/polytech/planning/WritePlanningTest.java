package com.polytech.planning;

import org.junit.BeforeClass;
import org.junit.Test;

import com.polytech.planning.controller.GeneratePlanning;
import com.polytech.planning.controller.WritePlanning;

public class WritePlanningTest {
	private static WritePlanning wp;
	private static GeneratePlanning gp;

	@BeforeClass
	public static void init() {
		gp = new GeneratePlanning("2017/2018", "Maquette.xlsx", "Calendar.xlsx");
		try {
			wp = new WritePlanning(gp.getPlanningByYear("DI4"), "DI4", "TestWriteTeachingUnits.xlsx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testWriteTeachingUnit() {
		wp.createFile();
	}
}
