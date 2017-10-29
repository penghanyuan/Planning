package com.polytech.planning.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public final class StylesLib {

	public static XSSFCellStyle baseStyle(XSSFWorkbook wb) {
		// create font
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Arial");
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBold(false);
		font.setItalic(false);
		
		// Create cell style
		XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);	
		//cellStyle.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
		//cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		
		// Setting font to style
		cellStyle.setFont(font);

		return cellStyle;
	}
}
