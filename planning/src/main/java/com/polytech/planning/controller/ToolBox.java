package com.polytech.planning.controller;

import java.util.List;

public final class ToolBox {

	public static String listToString(List<?> list) {
		String result = "";
		for (int i = 0; i < list.size(); i++) {
			result += "" + list.get(i);
		}
		return result;
	}
}
