package com.polytech.planning.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public abstract class ReadFile {

	private Workbook wb;
	private File file;

	/**
	 * @param filePath
	 *            full path of the file to read
	 */
	public ReadFile(String filePath) {
		this.file = new File(filePath);
		try {
			this.wb = WorkbookFactory.create(file);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param rowNum
	 *            Number of row to be readed
	 * @param colNum
	 *            Number of column to be readed
	 * @param sheetNum
	 *            Number of the sheet to be readed
	 */
	protected String readCell(int rowNum, int colNum, int sheetNum) {
		try {
			wb = WorkbookFactory.create(file);

			// Sheet, row and cell where read the content
			DataFormatter formatter = new DataFormatter();

			Sheet sheet = wb.getSheetAt(sheetNum);
			Row row = sheet.getRow(rowNum);

			Cell cell = row.getCell(colNum);

			String cellContent;
			if (cell != null) {
				cellContent = formatter.formatCellValue(cell);
			} else {
				cellContent = null;
			}

			return cellContent;

		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * @param rowNb
	 * @param sheetNb
	 */
	protected List<String> readRow(int rowNb, int sheetNb) {
		List<String> readingValues = new ArrayList<String>();
		int lastCell;
		int numberCell;

		// Sheet, row and cell where read the content
		Sheet sheet = wb.getSheetAt(sheetNb);
		Row row = sheet.getRow(rowNb);

		try {
			if (rowIsEmpty(rowNb, sheetNb))
				return null;
		} catch (Exception e) {
			System.out.println("Cet onglet n'existe pas.");
			e.printStackTrace();
		}

		numberCell = row.getFirstCellNum();
		lastCell = row.getLastCellNum();

		while (numberCell <= lastCell) {
			readingValues.add(readCell(rowNb, numberCell, sheetNb));
			numberCell++;
		}

		return readingValues;
	}

	/**
	 * @param rowNb
	 * @param sheetNb
	 */
	protected boolean rowIsEmpty(int rowNb, int sheetNb) throws NullPointerException {
		Sheet sheet = wb.getSheetAt(sheetNb);
		Row row = sheet.getRow(rowNb);

		if (sheet.equals(null)) {
			throw new NullPointerException("L'onglet " + sheetNb + " n'existe pas");
		} else {
			if (row == null)
				return true;

			if (row.getLastCellNum() <= 0)
				return true;

			for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
				Cell cell = row.getCell(cellNum);
				if (cell != null && cell.getCellTypeEnum() != CellType.BLANK && !cell.toString().isEmpty())
					return false;
			}

			return true;
		}
	}

	/**
	 * @param lineNb
	 * @param rowNb
	 * @param sheetNb
	 */
	protected boolean cellIsEmpty(int colNum, int rowNum, int sheetNum) throws NullPointerException {
		Sheet sheet = wb.getSheetAt(sheetNum);
		Row row = sheet.getRow(rowNum);
		Cell cell;

		// DataFormatter formatter = new DataFormatter();
		String cellContent;

		if (sheet.equals(null)) {
			throw new NullPointerException("L'onglet " + sheetNum + " n'existe pas");
		} else {
			cell = row.getCell(rowNum);
			System.out.println(cell);
			if (cell != null) {
				cellContent = this.readCell(rowNum, colNum, sheetNum);
				if (cellContent == null) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	/**
	 * @param sheetNb
	 * @return A tqble with two values, the first the row number and the second the
	 *         column number of the first non-empty cell
	 */
	public int[] getFirstCellNotEmpty(int sheetNb) {
		int[] coordonates = new int[2];

		Sheet sheet = wb.getSheetAt(sheetNb);
		int lineNum = 0;
		Row row = sheet.getRow(lineNum);

		if (sheet.equals(null))
			throw new NullPointerException("L'onglet " + sheetNb + " n'existe pas");

		while (row == null) {
			row = sheet.getRow(lineNum);
			lineNum++;
		}

		coordonates[0] = lineNum;
		coordonates[1] = row.getFirstCellNum();

		return coordonates;
	}

	/**
	 * @param sheetNb
	 * @param content
	 * @return A tqble with two values, the first the row number and the second the
	 *         column number of the first content find
	 */
	public int[] searchContent(int sheetNb, String content) {
		int[] coordonates = new int[2];
		boolean find = false;
		int i = 0;

		Sheet sheet = wb.getSheetAt(sheetNb);
		int lineNum = 0;
		// Row row = sheet.getRow(lineNum);

		if (sheet.equals(null))
			throw new NullPointerException("L'onglet " + sheetNb + " n'existe pas");

		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellTypeEnum() == CellType.STRING) {
					if (cell.getRichStringCellValue().getString().trim().equals(content)) {
						coordonates[0] = cell.getRowIndex();
						coordonates[1] = cell.getColumnIndex();
					}
				}
			}
		}
		return coordonates;
	}
}
