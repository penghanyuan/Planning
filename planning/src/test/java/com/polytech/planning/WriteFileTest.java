package com.polytech.planning;

import org.junit.BeforeClass;
import org.junit.Test;

import com.polytech.planning.controller.WriteFile;


public class WriteFileTest {
	
	private static WriteFile wf;
	
	@BeforeClass
	public static void init(){
		wf = new WriteFile("writeFileTest.xlsx");
		
		int[] coordinates;
		coordinates[0] = 0;
		coordinates[1] = 0;
		wf.writeCell(coordinates, 0, "1");
		
	}
	
	@Test
	public void SumFormulaTest() {
		
	}

}
