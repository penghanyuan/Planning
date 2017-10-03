package com.polytech.planning.controller;

import java.util.HashMap;
import java.util.List;

import com.polytech.planning.model.OriginalCourse;

public class ReadMockUp extends ReadFile {

	public ReadMockUp(String filePath) {
		super(filePath);
	}
	
	public HashMap<String,List<OriginalCourse>> readData(){
		return null;
	}
}
