package com.polytech.planning.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.polytech.planning.model.Course;
import com.polytech.planning.model.OriginalCourse;
import com.polytech.planning.model.Teacher;
import com.polytech.planning.model.TeachingUnit;

public class ParserMockUp {

	private LinkedHashMap<String, List<OriginalCourse>> originalCourses;
	
	public ParserMockUp() {
		this.originalCourses = new LinkedHashMap<String, List<OriginalCourse>>();
	}

	/**
	 * @param originalCourses
	 */
	public ParserMockUp(LinkedHashMap<String, List<OriginalCourse>> originalCourses) {
		this.originalCourses = originalCourses;
	}

	/**
	 * create list of teaching units
	 * @return list of teaching units
	 */
	public List<TeachingUnit> createTeachingUnits() {
		List<TeachingUnit> teachingUnits = new ArrayList<TeachingUnit>();

		/*
		 * create list of teaching unit
		 */
		for (String key : originalCourses.keySet()) {
			TeachingUnit teachingUnit = new TeachingUnit(key);
			teachingUnit.setListCourses(this.createCourses(originalCourses.get(key)));
			teachingUnits.add(teachingUnit);
		}

		return teachingUnits;
	}

	/**
	 * 
	 * @param courses
	 * @return list of course objects
	 */
	private List<Course> createCourses(List<OriginalCourse> courses) {
		List<Course> coursesList = new ArrayList<Course>();
		for (OriginalCourse oc : courses) {
			Course c = new Course(oc.getCourseName());
			if (oc.getHoursCM() != null) {
				c.setTotalCM(oc.getHoursCM());
			}
			if (oc.getHoursTD() != null) {
				c.setTotalTD(oc.getHoursTD());
			}
			if (oc.getHoursTP() != null) {
				c.setTotalTP(oc.getHoursTP());
			}
			if (oc.isCc()) {
				c.setCc(true);
			}
			if (oc.isCt()) {
				c.setCt(true);
			}
			if (oc.isMundus()) {
				c.setMundus(true);
			}
			if (oc.getHoursProject() != null) {
				c.setTotalProject(oc.getHoursProject());
			}
			if (oc.getTeachers() != null) {
				Double[] heures = new Double[3];
				heures[0] = oc.getHoursCM();
				heures[1] = oc.getHoursTD();
				heures[2] = oc.getHoursTP();
				c.setListTeachers(this.createTeachers(oc.getTeachers(), heures));
			}
			coursesList.add(c);
			// System.out.println("Courses after parser :" + c.getName());
		}
		if (coursesList.isEmpty())
			return null;
		else
			return coursesList;
	}

	/**
	 * 
	 * @param oriTeachers
	 * @return list of teachers
	 */ 
	@SuppressWarnings("unused")
	public List<Teacher> createTeachers(String oriTeachers, Double[] heures) {
		String regCM = ".*CM.*", regTD = ".*TD.*", regTP = ".*TP.*", regAllMundus = ".*Mundus.*";
		List<Teacher> teachers = new ArrayList<Teacher>();

		String[] teachersInits = oriTeachers.split(";");
		LinkedHashMap<String, String[]> teacherAndCourses = new LinkedHashMap<String, String[]>();
		boolean fusion = false;
		if (!oriTeachers.matches(regCM) && !oriTeachers.matches(regTD) && !oriTeachers.matches(regTP)
				&& !oriTeachers.matches(regAllMundus)) {
			fusion = true;
		}
		/*
		 * split by '+' and ',' to get each part of the data exemple:
		 * 
		 */
		for (int i = 0; i < teachersInits.length; i++) {
			teacherAndCourses.put(teachersInits[i].split(",|\\+")[0], teachersInits[i].split(",|\\+"));
		}

		for (String teacherName : teacherAndCourses.keySet()) {

			// System.out.println(teacherName.trim());
			Teacher teacher = new Teacher(teacherName.trim());

			String[] tempTeacher = teacherAndCourses.get(teacherName);
			int flag = 0, flagMundus = 0;
			for (int i = 1; i < tempTeacher.length; i++) {
				// System.out.println(tempTeacher[i]);

				// System.out.println(tempTeacher[i].trim());

				String tempTeacherNoSpace = tempTeacher[i].trim();

				if (tempTeacherNoSpace.matches(regCM)) {
					flag++;
					// CM
					Pattern pattern = Pattern.compile("(\\d*)(h.*CM)");
					Matcher matcher = pattern.matcher(tempTeacherNoSpace);
					if (matcher.find()) {
						int hoursCM = Integer.parseInt(matcher.group(1));
						// System.out.println(hoursCM);
						teacher.setHoursCM(hoursCM);
					}

				}

				if (tempTeacherNoSpace.matches(regTD)) {
					flag++;
					// TD
					Pattern pattern = Pattern.compile("(\\d*)(h.*TD)");
					Matcher matcher = pattern.matcher(tempTeacherNoSpace);
					if (matcher.find()) {
						int hoursTD = Integer.parseInt(matcher.group(1));
						// System.out.println(hoursTD);
						if (tempTeacherNoSpace.matches(regAllMundus)) {
							// this part is for mundus
							teacher.setTDMundus(hoursTD);
						} else {
							teacher.setHoursTD(hoursTD);
						}
					}

				}

				if (tempTeacherNoSpace.matches(regTP)) {
					flag++;
					// TP
					Pattern pattern = Pattern.compile("(\\d*)(h.*TP)");
					Matcher matcher = pattern.matcher(tempTeacherNoSpace);
					if (matcher.find()) {
						int hoursTP = Integer.parseInt(matcher.group(1));
						// System.out.println(hoursTP);

						if (tempTeacherNoSpace.matches(regAllMundus)) {
							// this part is for mundus
							teacher.setTPMundus(hoursTP);
						} else {
							teacher.setHoursTP(hoursTP);
						}
					}

				}

				if (tempTeacherNoSpace.matches("^Mundus")) {
					// This teacher do all tp/td time with mundus
					teacher.setTDMundus(teacher.getHoursTD());
					teacher.setTPMundus(teacher.getHoursTP());
					flagMundus++;
				}
			}
			if (flag == 0) {
				// no tp, no td, no cm
				// means do all
				teacher.setHoursCM(heures[0]);
				teacher.setHoursTD(heures[1]);
				teacher.setHoursTP(heures[2]);
				if (flagMundus != 0) {
					teacher.setTDMundus(heures[1]);
					teacher.setTPMundus(heures[2]);
				}
			}
			teachers.add(teacher);
			if (fusion) {
				teachers = this.fusionTeacher(teachers);
			}
			// System.out.println("Teacher after parser :" + teacher.getName());
		}

		return teachers;
	}
	/**
	 * create list of teacher seperated by "/" for a same course
	 * eg.N. Monmarché / P. Gaucher / Y. Kergosien for DI3 S5
	 * @param oriTeachers
	 * @return
	 */

	private List<Teacher> fusionTeacher(List<Teacher> oriTeachers) {
		List<Teacher> result = new ArrayList<Teacher>();
		Teacher t = new Teacher();
		String name = "";
		for (Teacher teacher : oriTeachers) {
			name = name + teacher.getName() + "/";
		}
		if (name != null && name != "")
			name = name.substring(0, name.length() - 1);
		t.setName(name);
		t.setHoursCM(oriTeachers.get(0).getHoursCM());
		t.setHoursTD(oriTeachers.get(0).getHoursTD());
		t.setHoursTP(oriTeachers.get(0).getHoursTP());
		t.setHoursProjet(oriTeachers.get(0).getHoursProjet());
		result.add(t);

		for (Teacher teacher : oriTeachers) {
			if (teacher.getTDMundus() != 0 || teacher.getTPMundus() != 0) {
				Teacher tmundus = new Teacher();
				tmundus.setName(teacher.getName());
				tmundus.setTDMundus(teacher.getTDMundus());
				tmundus.setTPMundus(teacher.getTPMundus());
				result.add(tmundus);
			}
		}
		return result;
	}
}
