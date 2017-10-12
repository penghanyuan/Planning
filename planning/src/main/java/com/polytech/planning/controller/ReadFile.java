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
			DataFormatter formatter = new DataFormatter();
			
			Sheet sheet = wb.getSheetAt(sheetNb);
			Row row = sheet.getRow(rowNb);

			Cell cell = row.getCell(columnNb);
			
			String cellContent = formatter.formatCellValue(cell);
			
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
			if(rowIsEmpty(rowNb, sheetNb))
				return null;
		} catch (Exception e) {
			System.out.println("Cet onglet n'existe pas.");
			e.printStackTrace();
		}
		
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
	protected boolean rowIsEmpty(int rowNb, int sheetNb) throws Exception {
		Sheet sheet = wb.getSheetAt(sheetNb);
		Row row = sheet.getRow(rowNb);
		
		if(sheet.equals(null)) {
			throw new Exception();
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
	protected boolean cellIsEmpty(int lineNb, int rowNb, int sheetNb) throws Exception {
		Sheet sheet = wb.getSheetAt(sheetNb);
		Row row = sheet.getRow(rowNb);
		
		if(sheet.equals(null)) {
			throw new Exception();
		} else {
			if (row.equals(null))
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
}
