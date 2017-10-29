package com.polytech.planning;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.polytech.planning.controller.StylesLib;


public class StyleLibTest {
	private static String path;
	private static XSSFWorkbook workbook;

	@BeforeClass
	public static void init() {
		path = "TestStyles.xlsx";
		workbook = new XSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Test de styles");
		Row row = sheet1.createRow(0);
		Cell cell = row.createCell(0);
		Row rowCM = sheet1.createRow(1);
		Cell cellCM = rowCM.createCell(0);
		Row rowTD = sheet1.createRow(2);
		Cell cellTD = rowTD.createCell(0);
		Row rowTP = sheet1.createRow(3);
		Cell cellTP = rowTP.createCell(0);

		sheet1.setColumnWidth(0, 24 * 200);
		row.setHeightInPoints(24);
		rowCM.setHeightInPoints(24);
		rowTD.setHeightInPoints(24);
		rowTP.setHeightInPoints(24);

		cell.setCellValue("Style de base");
		cell.setCellStyle(StylesLib.baseStyle(workbook));
		
		cellCM.setCellValue("Style CM");
		cellCM.setCellStyle(StylesLib.cmStyle(workbook));
		
		cellTD.setCellValue("Style TD");
		cellTD.setCellStyle(StylesLib.tdStyle(workbook));
		
		cellTP.setCellValue("Style TP");
		cellTP.setCellStyle(StylesLib.tpStyle(workbook));

		FileOutputStream output;
		try {
			output = new FileOutputStream(path);
			workbook.write(output);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		System.out.println("Coucou");
		Assert.assertTrue(true);
	}
}
