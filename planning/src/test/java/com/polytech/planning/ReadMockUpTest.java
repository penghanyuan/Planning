package com.polytech.planning;

import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.DynAnyPackage.InvalidValue;

import com.polytech.planning.controller.ReadMockUp;

public class ReadMockUpTest {
	private static ReadMockUp readMockUp;
	// private static OriginalTeachingUnit expectedTeachingUnit;

	@BeforeClass
	public static void init() {
		readMockUp = new ReadMockUp("Maquette.xlsx", 1);
		// expectedTeachingUnit = new OriginalTeachingUnit();
		// expectedTeachingUnit.setName(null);
	}
	
	@Test
	public void readTeachingUnits() {
		readMockUp.readTeachingUnits();
	}
}
