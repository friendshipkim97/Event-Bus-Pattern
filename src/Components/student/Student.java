/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.student;

import Components.constant.Constants.EStudent;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Student {
	protected String studentId;
	protected String name;
	protected String department;
	protected ArrayList<String> completedCoursesList = new ArrayList<String>();;

	public Student(String inputString) {
		StringTokenizer stringTokenizer = new StringTokenizer(inputString);
		this.studentId = stringTokenizer.nextToken();
		this.name = stringTokenizer.nextToken();
		this.name = this.name + EStudent.eSpace.getContent() + stringTokenizer.nextToken();
		this.department = stringTokenizer.nextToken();
		while (stringTokenizer.hasMoreTokens()) {
			this.completedCoursesList.add(stringTokenizer.nextToken()); } }

	public boolean match(String studentId) {
		return this.studentId.equals(studentId); }
	public String getName() {
		return this.name;
	}
	public ArrayList<String> getCompletedCourses() {
		return this.completedCoursesList; }
	public String getString() {
		String stringReturn = this.studentId + EStudent.eSpace.getContent() + this.name + EStudent.eSpace.getContent() + this.department;
		for (int i = EStudent.eZero.getNumber(); i < this.completedCoursesList.size(); i++)
			stringReturn += EStudent.eSpace.getContent() + this.completedCoursesList.get(i).toString();
		return stringReturn; }
}
