/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University
 */
package Components.Course;

import Components.Constant.Constants.ECourseComponent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CourseComponent {

    protected ArrayList<Course> vCourse;

    public CourseComponent(String sCourseFileName) throws FileNotFoundException, IOException { 	
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(sCourseFileName));       
        this.vCourse  = new ArrayList<Course>();
        while (bufferedReader.ready()) {
            String courseInfo = bufferedReader.readLine();
            if(!courseInfo.equals(ECourseComponent.eEmpty.getContent())) this.vCourse.add(new Course(courseInfo)); }
        bufferedReader.close(); }
    
    public ArrayList<Course> getCourseList() {
        return this.vCourse;
    }
    
    public boolean isRegisteredCourse(String courseId) {
        for (int i = ECourseComponent.eZero.getNumber(); i < this.vCourse.size(); i++) {
            if(((Course) this.vCourse.get(i)).match(courseId)) return true; }
        return false; }

    public void deleteCourse(String message) {
        for(int i = ECourseComponent.eZero.getNumber(); i < this.vCourse.size(); i++){
            if(this.vCourse.get(i).getCourseId().equals(message)){
                this.vCourse.remove(i); } } }

    public Course getCourse(String courseId){
        for (int i = ECourseComponent.eZero.getNumber(); i < this.vCourse.size(); i++) {
            if(((Course) this.vCourse.get(i)).match(courseId)) return this.vCourse.get(i); }
        return null; }
}