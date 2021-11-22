/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Components.constant.Constants.EStudentMain;
import framework.Event;
import framework.EventId;
import framework.EventQueue;
import framework.RMIEventBus;

public class StudentMain {
	public static void main(String args[]) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup(EStudentMain.eEventBus.getContent());
		long componentId = eventBus.register();
		System.out.println(EStudentMain.eStudentMainID.getContent() + componentId + EStudentMain.eStudentMainRegister.getContent());

		StudentComponent studentsList = new StudentComponent(EStudentMain.eStudentFileName.getContent());
		Event event = null;

		boolean done = false;
		while (!done) {
			try {
				Thread.sleep(EStudentMain.eThreadTime.getNumber());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = EStudentMain.eOne.getNumber(); i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
				case ListStudents:
					printLogEvent(EStudentMain.eEventGet.getContent(), event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, makeStudentList(studentsList)));
					break;
				case RegisterStudents:
					printLogEvent(EStudentMain.eEventGet.getContent(), event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerStudent(studentsList, event.getMessage())));
					break;
				case DeleteStudents:
					printLogEvent(EStudentMain.eEventDelete.getContent(), event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, deleteStudent(studentsList, event.getMessage())));
					break;
				case validationApplicationForCourse:
					printLogEvent(EStudentMain.eEventValidation.getContent(), event);
					String[] inputData = getInputData(event.getMessage());
					String inputCourseNumber = inputData[EStudentMain.eZero.getNumber()];
					String inputStudentNumber = inputData[EStudentMain.eOne.getNumber()];
					ArrayList<String> advancedCourseNumbers = new ArrayList<>();
					for (int j = EStudentMain.eTwo.getNumber(); j<inputData.length; j++) {
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
                    else eventBus.sendEvent(new Event(EventId.ClientOutput, applicationForCourse(studentsList, inputCourseNumber, inputStudentNumber)));
					break;
                case QuitTheSystem:
					printLogEvent(EStudentMain.eEventGet.getContent(), event);
					eventBus.unRegister(componentId);
					done = EStudentMain.eTrue.getCheck();
					break;
				default:
					break;
				}
			}
		}
	}

	private static String makeStudentList(StudentComponent studentsList) {
		String returnString = EStudentMain.eEmpty.getContent();
		for (int j = EStudentMain.eZero.getNumber(); j < studentsList.vStudent.size(); j++) {
			returnString += studentsList.getStudentList().get(j).getString() + EStudentMain.eEnter.getContent(); }
		return returnString;
	}
	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				EStudentMain.eLogEventMessage1.getContent() + comment + EStudentMain.eLogEventMessage2.getContent()
						+ event.getEventId() + EStudentMain.eLogEventMessage3.getContent() + event.getMessage());
	}

	private static String[] getInputData(String message) {
		Scanner scanner = new Scanner(message);
		String s = scanner.nextLine();
		String[] distinguishedMessage = s.split(EStudentMain.eMessageSplitFormat.getContent());
		return distinguishedMessage;
	}

	/**
	 * validation
	 */

	private static String deleteStudent(StudentComponent studentsList, String message) {
		if (studentsList.isRegisteredStudent(message)) {
			studentsList.deleteStudent(message);
			return EStudentMain.eDeleteStudentSuccessMessage.getContent();
		} else
			return EStudentMain.eDeleteStudentFailMessage.getContent();
	}
	
	private static String registerStudent(StudentComponent studentsList, String message) {
		Student student = new Student(message);
		if (!studentsList.isRegisteredStudent(student.studentId)) {
			studentsList.vStudent.add(student);
			return EStudentMain.eRegisterStudentSuccessMessage.getContent();
		} else
			return EStudentMain.eRegisterStudentFailMessage.getContent();
	}

	private static String applicationForCourse(StudentComponent studentsList, String inputCourseNumber, String inputStudentNumber) {
		studentsList.applicationForCourse(inputCourseNumber, inputStudentNumber);
		return EStudentMain.eApplicationForCourseSuccessMessage.getContent();
	}

	private static String validationStudentNumberIsRegistered(StudentComponent studentsList, String inputStudentNumber) {
		if (!studentsList.isRegisteredStudent(inputStudentNumber))
			return EStudentMain.eStudentNumberNotRegisteredMessage.getContent();
		return EStudentMain.eEmpty.getContent(); }

	private static String validationTakingCourse(StudentComponent studentsList, String inputCourseNumber, String inputStudentNumber) {
		if (studentsList.isAlreadyTakingCourse(inputCourseNumber, inputStudentNumber))
		     return EStudentMain.eAlreadyTakingCourseMessage.getContent();
		return EStudentMain.eEmpty.getContent(); }

	private static String validationCompletedAdvancedCourses(StudentComponent studentsList
			, ArrayList<String> advancedCourseNumbers, String inputStudentNumber) {
		if(!studentsList.isCompletedAdvancedCourse(advancedCourseNumbers, inputStudentNumber))
			return EStudentMain.eNotCompletedAdvancedCourses.getContent();
		return EStudentMain.eEmpty.getContent(); }
}
