package com.polytech.planning.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.omg.DynamicAny.DynAnyPackage.InvalidValue;

import com.polytech.planning.model.Holiday;

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

	public static LinkedHashMap<Integer, String> getBetweenDates(Date begin, Date end) {
		LinkedHashMap<Integer, String> result = new LinkedHashMap<Integer, String>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(begin);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yy");
		while (begin.getTime() <= end.getTime()) {
			result.put(tempStart.get(Calendar.WEEK_OF_YEAR), sdf.format(tempStart.getTime()));
			tempStart.add(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
			begin = tempStart.getTime();
		}
		return result;
	}

	public static boolean isHoliday(String string, List<Holiday> holiday) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yy");
		Date date;
		try {
			date = sdf.parse(string);

			for (Holiday h : holiday) {
				// System.out.println("date " + date);
				// System.out.println("date " + date.getTime());
				// System.out.println("holiday " + h.getStartDate().getTime());
				if (date.getTime() >= h.getStartDate().getTime() && date.getTime() < h.getEndDate().getTime()) {
					return true;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}
}
