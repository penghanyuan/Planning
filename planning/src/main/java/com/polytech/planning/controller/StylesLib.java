package com.polytech.planning.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public final class StylesLib {

	private static XSSFColor cmBg;
	private static XSSFColor tdBg;
	private static XSSFColor tpBg;
	private static XSSFColor ccEtBg;
	private static XSSFColor dateBg;
	private static XSSFColor weekNumBg;
	private static XSSFColor holidaysBg;

	public static XSSFCellStyle baseStyle(XSSFWorkbook wb) {
		// Define color palette
		cmBg = new XSSFColor(new java.awt.Color(33, 150, 243, 0)); // #2196F3
		tdBg = new XSSFColor(new java.awt.Color(139, 195, 74, 0)); // #8BC34A
		tpBg = new XSSFColor(new java.awt.Color(255, 193, 7, 0)); // #FFC107
		ccEtBg = new XSSFColor(new java.awt.Color(244, 67, 54, 0)); // #F44336

		dateBg = new XSSFColor(new java.awt.Color(189, 189, 189, 0)); // #BDBDBD
		weekNumBg = new XSSFColor(new java.awt.Color(224, 224, 224, 0)); // #E0E0E0
		holidaysBg = new XSSFColor(new java.awt.Color(255, 87, 34, 0)); // #FF5722

		// create font
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Arial");
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBold(false);
		font.setItalic(false);

		// Create cell style
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// Setting font to style
		cellStyle.setFont(font);

		return cellStyle;
	}

	public static XSSFCellStyle cmStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(cmBg);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	public static XSSFCellStyle tdStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(tdBg);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	public static XSSFCellStyle tpStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(tpBg);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	public static XSSFCellStyle ccStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(ccEtBg);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	public static XSSFCellStyle weekNumStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(weekNumBg);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	public static XSSFCellStyle dateStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(dateBg);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	public static XSSFCellStyle holidayStyle(XSSFWorkbook wb) {
		XSSFCellStyle cellStyle = baseStyle(wb);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setFillForegroundColor(holidaysBg);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	public static XSSFCellStyle setBold(XSSFCellStyle cellStyle) {
		Font font = cellStyle.getFont();
		font.setBold(true);
		
		cellStyle.setFont(font);

		return cellStyle;
	}
}
