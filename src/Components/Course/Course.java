/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University
 */
package Components.Course;

import Components.Constant.Constants.ECourse;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Course {
    protected String courseId;
	protected String instructor;
    protected String name;
    protected ArrayList<String> prerequisiteCoursesList;
    
    public Course(String inputString) {
        StringTokenizer stringTokenizer = new StringTokenizer(inputString);
        this.courseId = stringTokenizer.nextToken();
        this.instructor = stringTokenizer.nextToken();
        this.name = stringTokenizer.nextToken();
        this.prerequisiteCoursesList = new ArrayList<String>();
        while(stringTokenizer.hasMoreTokens()) {
            this.prerequisiteCoursesList.add(stringTokenizer.nextToken()); } }

    public ArrayList<String> getPrerequisiteCoursesList() { return prerequisiteCoursesList; }
    public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
    public boolean match(String courseId) {
        return this.courseId.equals(courseId);
    }
    public String getName() {
        return this.name;
    }
    public String getString() {
        String stringReturn = this.courseId + ECourse.eSpace.getContent() + this.instructor + ECourse.eSpace.getContent() + this.name;
        for(int i = ECourse.eZero.getNumber(); i < this.prerequisiteCoursesList.size(); i++) {
            stringReturn += ECourse.eSpace.getContent() + this.prerequisiteCoursesList.get(i).toString(); }
        return stringReturn; }
}
