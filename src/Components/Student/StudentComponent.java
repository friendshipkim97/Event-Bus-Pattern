/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Student;

import Components.Constant.Constants.EStudentComponent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StudentComponent {

	protected ArrayList<Student> vStudent;

	public StudentComponent(String sStudentFileName) throws FileNotFoundException, IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(sStudentFileName));
		this.vStudent = new ArrayList<Student>();
		while (bufferedReader.ready()) {
			String stuInfo = bufferedReader.readLine();
			if (!stuInfo.equals(EStudentComponent.eEmpty.getContent())) this.vStudent.add(new Student(stuInfo));
		}bufferedReader.close(); }

	public ArrayList<Student> getStudentList() {
		return vStudent;
	}
	public void setStudent(ArrayList<Student> vStudent) {
		this.vStudent = vStudent;
	}

	public boolean isRegisteredStudent(String sSID) {
		for (int i = EStudentComponent.eZero.getNumber(); i < this.vStudent.size(); i++) {
			if (((Student) this.vStudent.get(i)).match(sSID)) return true; }
		return false; }

	public void deleteStudent(String message) {
		for (int i = EStudentComponent.eZero.getNumber(); i < this.vStudent.size(); i++) {
			if(this.vStudent.get(i).match(message)){
				this.vStudent.remove(i); } } }

	public boolean isAlreadyTakingCourse(String inputCourseNumber, String inputStudentNumber) {
		for (int i = EStudentComponent.eZero.getNumber(); i < this.vStudent.size(); i++) {
			if(this.vStudent.get(i).match(inputStudentNumber)) {
				for (String completedCourse : this.vStudent.get(i).getCompletedCourses()) {
					if (completedCourse.equals(inputCourseNumber)) return true; } } }
		return false; }

	public boolean isCompletedAdvancedCourse(ArrayList<String> advancedCourseNumbers, String inputStudentNumber) {
		int index = EStudentComponent.eZero.getNumber();
		int advancedCount = EStudentComponent.eZero.getNumber();
		// get index
		index = getStudentIndex(inputStudentNumber);
		// check advancedCourses
		for (String advancedCourseNumber : advancedCourseNumbers) {
			for (String completedCourse : this.vStudent.get(index).getCompletedCourses()) {
				if(completedCourse.equals(advancedCourseNumber)) advancedCount++; } }
		if(advancedCount == advancedCourseNumbers.size())
			return true;
		return false; }

	public int getStudentIndex(String inputStudentNumber) {
		for (int i = EStudentComponent.eZero.getNumber(); i < this.vStudent.size(); i++) {
			if (this.vStudent.get(i).match(inputStudentNumber)) {
				return i; } }
		return EStudentComponent.eMinusOne.getNumber(); }

	public void applicationForCourse(String inputCourseNumber, String inputStudentNumber) {
		this.vStudent.get(getStudentIndex(inputStudentNumber)).getCompletedCourses().add(inputCourseNumber); }
}
