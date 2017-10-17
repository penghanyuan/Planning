package com.polytech.planning;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.DynAnyPackage.InvalidValue;

import com.polytech.planning.controller.ReadMockUp;
import com.polytech.planning.model.OriginalTeachingUnit;

public class ReadMockUpTest {
	private static ReadMockUp readMockUp;
	private static OriginalTeachingUnit expectedTeachingUnit; 

	@BeforeClass
	public static void init() {
		readMockUp = new ReadMockUp("Maquette.xlsx");
		expectedTeachingUnit = new OriginalTeachingUnit();
		expectedTeachingUnit.setName(null);
	}
	
	@Test
	public void readMockup() throws InvalidValue{
		readMockUp.readCourses(7,2,1);
		Assert.assertTrue(readMockUp.equals(expectedTeachingUnit));
	}
	
	@Test(expected = InvalidValue.class)
	public void readMockupBadCoordonates() throws InvalidValue {
		readMockUp.readCourses(-2,2,1);
	}
}
