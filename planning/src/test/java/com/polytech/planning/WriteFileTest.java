package com.polytech.planning;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.polytech.planning.controller.WriteFile;

public class WriteFileTest {

	private static WriteFile wf;
	private static Workbook wb;
	private static Sheet sheet;
	private static int[] coord;
	private static int[] formula;

	@BeforeClass
	public static void init() {
		wf = new WriteFile("TestWriteFile.xlsx");
		wb = wf.getWorkbook();
		sheet = wb.createSheet("Test de somme");

		int[] coordinates = { 0, 0 };
		int[] coordinatesFormula = { 10, 2 };
		coord = coordinates;
		formula = coordinatesFormula;

		wf.writeNumberCell(coord, sheet, 1);

		for (int i = 0; i < 15; i++) {
			coord[0]++;
			wf.writeNumberCell(coord, sheet, coord[0]);
		}

		System.out.println(coord[0]);
	}

	@Test
	public void SumFormulaTest() {
		int[] startFormula = { 0, 0 };
		int[] endFormula = coord;
		wf.writeSumFormula(startFormula, endFormula, formula, sheet);
	}

	@AfterClass
	public static void after() {
		wf.generateFile();
	}

}
