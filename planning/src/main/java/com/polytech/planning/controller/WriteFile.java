package com.polytech.planning.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.polytech.planning.model.Planning;

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

		// ecriture dans le fichier (creation des sheets)
	}

}
