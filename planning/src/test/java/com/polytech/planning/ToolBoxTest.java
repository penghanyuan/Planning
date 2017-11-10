package com.polytech.planning;

import org.junit.Assert;
import org.junit.Test;

import com.polytech.planning.controller.ToolBox;


public class ToolBoxTest {

	@Test
	public void capitalize() {
		String str = "pommes";
		String output = "Pommes";
		str = ToolBox.capitalize(str);
		
		Assert.assertEquals(output, str);
	}
	
	@Test
	public void getColLetter() {
		Assert.ass ToolBox.getColLetter(5));
		System.out.println(ToolBox.getColLetter(25));
		System.out.println(ToolBox.getColLetter(23));
		System.out.println(ToolBox.getColLetter(32));
	}
}
