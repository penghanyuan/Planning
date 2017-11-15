package com.polytech.planning.controller;

import java.util.List;

import org.omg.DynamicAny.DynAnyPackage.InvalidValue;

public final class ToolBox {

	public static String listToString(List<?> list) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += "" + list.get(i);
		}
		return result;
	}

	public static String capitalize(String input) {
		input = input.toLowerCase();
		String output = input.substring(0, 1).toUpperCase() + input.substring(1);
		return output;
	}

	public static String getColLetter(int num) throws InvalidValue {
		String result = "";
		
		if (num == 0) {
			return "A";
		} else if (num > 0) {
			while (num > 0) {
				int remainder = num % 26;
				System.out.println(remainder);
				char digit = (char) (remainder + 65);
				result = digit + result;
				num = (num - remainder) / 26;
			}
		} else {
			throw new InvalidValue();
		}

		return result;
	}
}
