package com.polytech.planning.controller;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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

	protected String readCell(int line, int row, int sheet) {

		return null;
	}

	protected String[] readRow(int row, int sheet) {
		return null;
	}
}
