package com.polytech.planning.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public final class StylesLib {

	public static CellStyle baseStyle(XSSFWorkbook wb) {
		// create font
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Arial");
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBold(false);
		font.setItalic(false);
		
		// Create cell style
		CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);	
		
		// Setting font to style
		cellStyle.setFont(font);

		return cellStyle;
	}
	
	public static CellStyle cmStyle(XSSFWorkbook wb) {
		CellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}
	
	public static CellStyle tdStyle(XSSFWorkbook wb) {
		CellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN1.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}
	
	public static CellStyle tpStyle(XSSFWorkbook wb) {
		CellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(IndexedColors.YELLOW1.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}
}
