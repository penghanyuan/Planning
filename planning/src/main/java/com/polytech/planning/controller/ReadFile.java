package com.polytech.planning.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ReadFile {

	private XSSFWorkbook wb;
	private File file;

	/**
	 * Constructor
	 * 
	 * @param filePath
	 *            full path of the file to read
	 */
	public ReadFile(String filePath) {
		this.file = new File(filePath);
		try {
			InputStream st = new FileInputStream(this.file);
			this.wb = new XSSFWorkbook(st);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To read a string cell
	 * 
	 * @param rowNum
	 *            Number of row to be readed
	 * @param colNum
	 *            Number of column to be readed
	 * @param sheetNum
	 *            Number of the sheet to be readed
	 */
	protected String readCell(int rowNum, int colNum, int sheetNum) {
		try {
			DataFormatter formatter = new DataFormatter();

			Sheet sheet = wb.getSheetAt(sheetNum);
			Row row = sheet.getRow(rowNum);

			Cell cell = row.getCell(colNum);
			// Cell nestCell = sheet.getRow(rowNum + 1).getCell(colNum);
			// System.out.println("row " + (rowNum + 1) + ",type: " +
			// nestCell.getCellTypeEnum().toString());
			String cellContent;
			if (cell != null) {
				cellContent = formatter.formatCellValue(cell);
			} else {
				cellContent = null;
			}

			return cellContent;

		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * To read a numeric cell
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param sheetNum
	 * @return
	 */
	protected Double readNumericCell(int rowNum, int colNum, int sheetNum) {
		try {

			Sheet sheet = wb.getSheetAt(sheetNum);
			Row row = sheet.getRow(rowNum);

			Cell cell = row.getCell(colNum);

			Double cellContent = null;
			if (cell.getCellTypeEnum() == CellType.BLANK) {
				cellContent = 0.0;
				return cellContent;
			}
			if (cell.getCellTypeEnum() == CellType.NUMERIC) {
				cellContent = cell.getNumericCellValue();
				return cellContent;
			} else {
				throw new NumberFormatException("La cellule ligne " + rowNum + " / colonne " + colNum + " de l'onglet "
						+ sheetNum + ", est de type " + getCellType(rowNum, colNum, sheetNum));
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * To read a cell with type date
	 * 
	 * @param rowNum
	 *            Number of row to be readed
	 * @param colNum
	 *            Number of column to be readed
	 * @param sheetNum
	 *            Number of the sheet to be readed
	 */
	protected Date readCellDate(int rowNum, int colNum, int sheetNum) {
		try {

			Sheet sheet = wb.getSheetAt(sheetNum);
			Row row = sheet.getRow(rowNum);

			Cell cell = row.getCell(colNum);

			Date cellContent = null;

			if (cell.getCellTypeEnum() == CellType.NUMERIC) {
				if (cell != null) {
					cellContent = cell.getDateCellValue();
					return cellContent;
				}
			} else {
				throw new Exception("La cellule n'est pas une date");
			}

		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * To read a row
	 * 
	 * @param rowNb
	 *            Number of row to be readed
	 * @param sheetNb
	 *            Number of the sheet to be readed
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
			e.printStackTrace();
			e.getMessage();
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
	 * To know if a row id empty
	 * 
	 * @param rowNb
	 *            Number of row to be readed
	 * @param sheetNb
	 *            Number of the sheet to be readed
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
	 * To know if a cell is empty
	 * 
	 * @param colNum
	 *            Number of column to be readed
	 * @param rowNum
	 *            Number of row to be readed
	 * @param sheetNum
	 *            Number of the sheet to be readed
	 */
	protected boolean cellIsEmpty(int rowNum, int colNum, int sheetNum) throws NullPointerException {
		Sheet sheet = wb.getSheetAt(sheetNum);

		if (sheet.equals(null)) {
			throw new NullPointerException("L'onglet " + sheetNum + " n'existe pas");
		} else {

			CellType type = CellType.BLANK;

			if (getCellType(rowNum, colNum, sheetNum).equals(type)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * To know if a cell is numeric type or not
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param sheetNum
	 * @return
	 * @throws NullPointerException
	 */
	protected boolean cellIsNumeric(int rowNum, int colNum, int sheetNum) throws NullPointerException {
		Sheet sheet = wb.getSheetAt(sheetNum);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(colNum);
		CellType numeric = CellType.NUMERIC;

		if (cell.getCellTypeEnum().equals(numeric)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * To know if a cell is string type or not
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param sheetNum
	 * @return
	 * @throws NullPointerException
	 */
	protected boolean cellIsString(int rowNum, int colNum, int sheetNum) throws NullPointerException {
		Sheet sheet = wb.getSheetAt(sheetNum);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(colNum);
		CellType string = CellType.STRING;

		if (cell.getCellTypeEnum().equals(string)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * To know the cell type
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param sheetNum
	 * @return
	 */
	protected CellType getCellType(int rowNum, int colNum, int sheetNum) {
		Sheet sheet = wb.getSheetAt(sheetNum);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(colNum);

		return cell.getCellTypeEnum();
	}

	/**
	 * Get the coordonates of first cell not empty
	 * 
	 * @param sheetNb
	 *            Number of the sheet to be readed
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
	 * Get the coordonates of cell who contain the search content
	 * 
	 * @param sheetNb
	 *            Number of the sheet to be readed
	 * @param content
	 *            the search String
	 * @return A table with two values, the first the row number and the second the
	 *         column number of the first content find
	 */
	public int[] searchContent(int sheetNb, String content, boolean startWith) throws NullPointerException {
		int[] coordonates = new int[2];
		String buffer;
		coordonates[0] = -1;
		coordonates[1] = -1;

		content = normalizeText(content);

		Sheet sheet = wb.getSheetAt(sheetNb);

		if (sheet.equals(null))
			throw new NullPointerException("L'onglet " + sheetNb + " n'existe pas");

		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellTypeEnum() == CellType.STRING) {
					buffer = cell.getRichStringCellValue().getString().trim().toUpperCase();
					buffer = normalizeText(buffer);
					if (startWith) {
						if (buffer.startsWith(content)) {
							coordonates[0] = cell.getRowIndex();
							coordonates[1] = cell.getColumnIndex();
						}
					} else {
						if (buffer.equals(content)) {
							coordonates[0] = cell.getRowIndex();
							coordonates[1] = cell.getColumnIndex();
						}
					}

				}
			}
		}
		return coordonates;
	}

	/**
	 * Put the text in lower case and without accents
	 * 
	 * @param input
	 *            string to normalize
	 * @return the string normalizes
	 */
	protected String normalizeText(String input) {
		input = input.toLowerCase();
		input = Normalizer.normalize(input, Normalizer.Form.NFD);
		input = input.replaceAll("[^\\p{ASCII}]", "");

		return input;
	}
}
