/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Components.Course.Course;
import Components.Course.CourseComponent;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

public class StudentMain {
	public static void main(String args[]) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup("EventBus");
		long componentId = eventBus.register();
		System.out.println("** StudentMain(ID:" + componentId + ") is successfully registered. \n");

		StudentComponent studentsList = new StudentComponent("Students.txt");
		Event event = null;

		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = 0; i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
				case ListStudents:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeStudentList(studentsList)));
					break;
				case RegisterStudents:
					printLogEvent("Get", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerStudent(studentsList, event.getMessage())));
					break;
				case DeleteStudents:
					printLogEvent("Delete", event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, deleteStudent(studentsList, event.getMessage())));
					break;
				case validationStudentNumberAndAdvancedCourses:
					printLogEvent("validation", event);
					String[] inputData = getInputData(event.getMessage());
					String inputCourseNumber = inputData[0];
					String inputStudentNumber = inputData[1];
					ArrayList<String> advancedCourseNumbers = new ArrayList<>();
					for (int j = 2; j<inputData.length; j++) {
						advancedCourseNumbers.add(inputData[j]); }

					String validationStudentNumberIsRegistered = validationStudentNumberIsRegistered(studentsList, inputStudentNumber);
					String validationTakingCourse = validationTakingCourse(studentsList, inputCourseNumber, inputStudentNumber);
					String validationCompletedAdvancedCourses = validationCompletedAdvancedCourses(studentsList, advancedCourseNumbers, inputStudentNumber);

					if(!validationStudentNumberIsRegistered.isEmpty())
						eventBus.sendEvent(new Event(EventId.ClientOutput, validationStudentNumberIsRegistered));
					else if(!validationTakingCourse.isEmpty())
					    eventBus.sendEvent(new Event(EventId.ClientOutput, validationTakingCourse));
					else if(!validationCompletedAdvancedCourses.isEmpty())
					    eventBus.sendEvent(new Event(EventId.ClientOutput, validationCompletedAdvancedCourses));
                    else
					    eventBus.sendEvent(new Event(EventId.ClientOutput, applicationForCourse(studentsList, inputCourseNumber, inputStudentNumber)));
					break;
                case QuitTheSystem:
					printLogEvent("Get", event);
					eventBus.unRegister(componentId);
					done = true;
					break;
				default:
					break;
				}
			}
		}
	}

	private static String deleteStudent(StudentComponent studentsList, String message) {
		if (studentsList.isRegisteredStudent(message)) {
			studentsList.deleteStudent(message);
			return "This student is successfully deleted.";
		} else
			return "There is no student";
	}
	
	private static String registerStudent(StudentComponent studentsList, String message) {
		Student student = new Student(message);
		if (!studentsList.isRegisteredStudent(student.studentId)) {
			studentsList.vStudent.add(student);
			return "This student is successfully added.";
		} else
			return "This student is already registered.";
	}
	private static String makeStudentList(StudentComponent studentsList) {
		String returnString = "";
		for (int j = 0; j < studentsList.vStudent.size(); j++) {
			returnString += studentsList.getStudentList().get(j).getString() + "\n";
		}
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				"\n** " + comment + " the event(ID:" + event.getEventId() + ") message: " + event.getMessage());
	}

	private static String[] getInputData(String message) {
		Scanner scanner = new Scanner(message);
		String s = scanner.nextLine();
		String[] distinguishedMessage = s.split("\\s+");
		return distinguishedMessage;
	}

	private static String applicationForCourse(StudentComponent studentsList, String inputCourseNumber, String inputStudentNumber) {
		studentsList.applicationForCourse(inputCourseNumber, inputStudentNumber);
		return "You have completed the course application.";
	}


	/**
	 * validation
	 */
	private static String validationStudentNumberIsRegistered(StudentComponent studentsList, String inputStudentNumber) {
		if (!studentsList.isRegisteredStudent(inputStudentNumber))
			return "This student number is an unregistered student number.";
		return "";
	}

	private static String validationTakingCourse(StudentComponent studentsList, String inputCourseNumber, String inputStudentNumber) {
		if (studentsList.isAlreadyTakingCourse(inputCourseNumber, inputStudentNumber))
		     return "The student is already taking the course.";
		return "";
	}

	private static String validationCompletedAdvancedCourses(StudentComponent studentsList
			, ArrayList<String> advancedCourseNumbers, String inputStudentNumber) {
		if(!studentsList.isCompletedAdvancedCourse(advancedCourseNumbers, inputStudentNumber))
			return "The student did not complete the advanced courses.";
		return "";
	}
}
