package com.polytech.planning.controller;

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

	/**
	 * 
	 * @param startCell
	 * @param endCell
	 * @param coordonates
	 * @param sheet
	 */
	public void writeSumFormula(int[] startCell, int[] endCell, int[] coordonates, Sheet sheet) {
		int rowStart = startCell[0];
		int colStart = startCell[1];
		int rowEnd = endCell[0];
		int colEnd = startCell[1];
		String formula = ""; // SUM(colStartrowStart:colEndrowEnd)
		writeCell(coordonates, sheet, formula);
	}

	/**
	 * 
	 * @param coordonates
	 * @param sheet
	 * @param content
	 * @return
	 */
	public Cell writeCell(int[] coordonates, Sheet sheet, String content) {
		Row row = sheet.createRow(coordonates[0]);

		Cell cell = row.createCell(coordonates[1]);
		cell.setCellValue(content);

		return cell;
	}

}
