/**
 * 
 */
package com.polytech.planning.model;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteFile {

	private Workbook workbook;
	private String filePath;

	/**
	 * @param filePath
	 */
	public WriteFile(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Write Planning in to excel file.
	 * 
	 * @param planning
	 */
	public void WritePlanning(Planning planning) {
		// si file finit par xls ou xlsx
		if (filePath.substring(filePath.length() - 4).equals(".xls")) {
			workbook = new HSSFWorkbook();
		} else {
			workbook = new XSSFWorkbook();
		}

		try {
			// Create 2 sheets
			Sheet sheet1 = workbook.createSheet(planning.getSemesters().get(0).getName());
			Sheet sheet2 = workbook.createSheet(planning.getSemesters().get(1).getName());

			// ....
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void wrintingFile(String file) {

	}

}
