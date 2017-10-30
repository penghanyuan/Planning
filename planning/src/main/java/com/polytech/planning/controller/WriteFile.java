package com.polytech.planning.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteFile {

	private Workbook workbook;
	private String filePath;

	/**
	 * Constructor
	 * 
	 * @param filePath
	 */
	public WriteFile(String filePath) {
		this.filePath = filePath;

		// si file finit par xls ou xlsx
		if (filePath.substring(filePath.length() - 4).equals(".xls")) {
			workbook = new HSSFWorkbook();
		} else {
			workbook = new XSSFWorkbook();
		}
	}

	/**
	 * @return the workbook
	 */
	public Workbook getWorkbook() {
		return workbook;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

}
