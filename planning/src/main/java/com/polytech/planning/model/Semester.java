package com.polytech.planning.model;

import java.util.Date;
import java.util.List;

public class Semester {
	private String name;
	
	private Date startDate;
	private Date endDate;
	
	private List<Date> listPublicHoliday;
	private List<Holiday> listHoliday;
}
