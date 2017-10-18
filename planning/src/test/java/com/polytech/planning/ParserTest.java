package com.polytech.planning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.polytech.planning.controller.Parser;
import com.polytech.planning.model.Teacher;

/**
 * Unit test for simple App.
 */
public class ParserTest {

	private static String inputTeachers;
	private static List<Teacher> expected;
	private static Teacher teacherOne;

	@BeforeClass
	public static void init() {
		inputTeachers = "C.Tacquard,16hCM, 20hTD x3gr + Mundus, 16hTP x3gr + Mundus; M. Martineau,12hTP x3gr + Mundus";
		expected = new ArrayList<Teacher>();

		teacherOne = new Teacher("C.Tacquard");
		teacherOne.setHoursCM(16);
		teacherOne.setHoursTD(20);
		teacherOne.setHoursTP(16);
		teacherOne.setTDMundus(true);
		teacherOne.setTPMundus(true);

		expected.add(teacherOne);

		Teacher teacher2 = new Teacher("M. Martineau");
		teacher2.setHoursTP(12);
		teacher2.setTPMundus(true);

		expected.add(teacher2);
	}

	@Test
	public void testParseTeachers() {
		Parser parser = new Parser();
		List<Teacher> output = parser.createTeachers(inputTeachers);

		Iterator<Teacher> outputIt = output.iterator();
		Iterator<Teacher> expectedIt = expected.iterator();

		Assert.assertEquals(2, output.size());

		while (expectedIt.hasNext()) {
			Assert.assertTrue(outputIt.next().equals(expectedIt.next()));
		}
	}
}
