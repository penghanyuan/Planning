package com.polytech.planning.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadFile {

	private Workbook wb;
	private File file;

	/**
	 * @param filePath full path of the file to read
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
	 * @param rowNb Number of row to be readed
	 * @param columnNb Number of column to be readed
	 * @param sheetNb Number of the sheet to be readed
	 */
	protected String readCell(int rowNb, int columnNb, int sheetNb) {
		try {
			wb = WorkbookFactory.create(file);

			// Sheet, row and cell where read the content
			Sheet sheet = wb.getSheetAt(sheetNb);
			Row row = sheet.getRow(rowNb);

			Cell cell = row.getCell(columnNb);
			
			return cell.getStringCellValue();

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
		
		if(rowIsEmpty(rowNb, sheetNb))
			return null;
		
		numberCell = row.getFirstCellNum(); 
		lastCell = row.getLastCellNum(); 
	    
		while(numberCell <= lastCell) {
			readingValues.add(readCell(rowNb, numberCell, sheetNb));
			numberCell++;
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
