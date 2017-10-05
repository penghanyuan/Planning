package com.polytech.planning.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadFile {

	private String filePath;
	private Workbook wb;
	private File file;

	/**
	 * @param filePath
	 */
	public ReadFile(String filePath) {
		this.filePath = filePath;
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
	 * @param rowNb
	 * @param columnNb
	 * @param sheetNb
	 */
	protected String readCell(int rowNb, int lineNb, int sheetNb) {
		try {
			wb = WorkbookFactory.create(file);

			// Sheet, row and cell where read the content
			Sheet sheet = wb.getSheetAt(sheetNb);
			Row row = sheet.getRow(rowNb);

			Cell cell = row.getCell(lineNb);
			
			return cell.getStringCellValue();

		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @param rowNb
	 * @param sheetNb
	 */
	protected List<String> readRow(int rowNb, int sheetNb) {
		List<String> readingValues = new ArrayList<String>();
		int i = 0;
		int j = 0;
		
		int blankCells = 10;
		
		if(rowIsEmpty(rowNb, sheetNb))
			return null;
		
		while(i <= blankCells) {
			readingValues.add(readCell(rowNb, j, sheetNb));
			if(readingValues.get(j).isEmpty()) {
				i++;
			} else {
				i = 0;
			}
			j++;
		}

		return readingValues;
	}
	
	/**
	 * @param rowNb
	 * @param sheetNb
	 */
	protected boolean rowIsEmpty(int rowNb, int sheetNb) {
		Sheet sheet = wb.getSheetAt(sheetNb);
		Row row = sheet.getRow(rowNb);
		
	    if (row == null) {
	        return true;
	    }
	    if (row.getLastCellNum() <= 0) {
	        return true;
	    }
	    for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
	        Cell cell = row.getCell(cellNum);
	        if (cell != null && cell.getCellTypeEnum() != CellType.BLANK && !cell.toString().isEmpty()) {
	            return false;
	        }
	    }
	    return true;
	}
}
