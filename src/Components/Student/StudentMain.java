/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.Student;

import Components.Constant.Constants.EStudentMain;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentMain {

	public static void main(String args[]) throws IOException, NotBoundException {
		new StudentMain();
	}

	RMIEventBus eventBus;
	long componentId;
	StudentComponent studentsList;

	String inputCourseNumber;
	String inputStudentNumber;
	ArrayList<String> advancedCourseNumbers;
	boolean validationError;

	public StudentMain() throws IOException, NotBoundException {
		advancedCourseNumbers = new ArrayList<>();
		registerComponent();
		readStudentFile();
		setEvent();
	}

	private void registerComponent() throws RemoteException, NotBoundException, MalformedURLException {
		eventBus = (RMIEventBus) Naming.lookup(EStudentMain.eEventBus.getContent());
		componentId = eventBus.register();
		System.out.println(EStudentMain.eStudentMainID.getContent() + componentId + EStudentMain.eStudentMainRegister.getContent());
	}

	private void readStudentFile() throws IOException {
		studentsList = new StudentComponent(EStudentMain.eStudentFileName.getContent());
	}

	private void setEvent() throws RemoteException {
		Event event = null;
		boolean done = EStudentMain.eFalse.getCheck();
		while (!done) {
			try {
				Thread.sleep(EStudentMain.eThreadTime.getNumber());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = EStudentMain.eZero.getNumber(); i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
					case ListStudents:
						printLogEvent(EStudentMain.eEventGet.getContent(), event);
						eventBus.sendEvent(new Event(EventId.ClientOutput, makeStudentList(studentsList)));
						break;
					case RegisterStudents:
						printLogEvent(EStudentMain.eEventGet.getContent(), event);
						validationRegisterStudent(studentsList, event.getMessage());
						break;
					case DeleteStudents:
						printLogEvent(EStudentMain.eEventDelete.getContent(), event);
						validationDeleteStudent(studentsList, event.getMessage());
						break;
					case validationApplicationForCourse:
						validationError = EStudentMain.eFalse.getCheck();
						printLogEvent(EStudentMain.eEventValidation.getContent(), event);
						getInputData(event.getMessage());
						validationStudentNumberIsRegistered(studentsList, inputStudentNumber);
						validationTakingCourse(studentsList, inputCourseNumber, inputStudentNumber);
						validationCompletedAdvancedCourses(studentsList, advancedCourseNumbers, inputStudentNumber);
						validationTotalErrorCheck();
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

	private String makeStudentList(StudentComponent studentsList) {
		String returnString = EStudentMain.eEmpty.getContent();
		for (int j = EStudentMain.eZero.getNumber(); j < studentsList.vStudent.size(); j++) {
			returnString += studentsList.getStudentList().get(j).getString() + EStudentMain.eEnter.getContent(); }
		return returnString;
	}

	private void printLogEvent(String comment, Event event) {
		System.out.println(
				EStudentMain.eLogEventMessage1.getContent() + comment + EStudentMain.eLogEventMessage2.getContent()
						+ event.getEventId() + EStudentMain.eLogEventMessage3.getContent() + event.getMessage());
	}

	private String[] getInputData(String message) {
		Scanner scanner = new Scanner(message);
		String s = scanner.nextLine();
		String[] distinguishedMessage = s.split(EStudentMain.eMessageSplitFormat.getContent());
		inputCourseNumber = distinguishedMessage[EStudentMain.eZero.getNumber()];
		inputStudentNumber = distinguishedMessage[EStudentMain.eOne.getNumber()];
		for (int j = EStudentMain.eTwo.getNumber(); j<distinguishedMessage.length; j++) {
			advancedCourseNumbers.add(distinguishedMessage[j]); }
		return distinguishedMessage;
	}

	private String applicationForCourse(StudentComponent studentsList, String inputCourseNumber, String inputStudentNumber) {
		studentsList.applicationForCourse(inputCourseNumber, inputStudentNumber);
		return EStudentMain.eApplicationForCourseSuccessMessage.getContent(); }

	/**
	 * validation
	 */
	private void validationDeleteStudent(StudentComponent studentsList, String message) throws RemoteException {
		if (studentsList.isRegisteredStudent(message)) {
			studentsList.deleteStudent(message);
			eventBus.sendEvent(new Event(EventId.ClientOutput, EStudentMain.eDeleteStudentSuccessMessage.getContent()));
		} else eventBus.sendEvent(new Event(EventId.ClientOutput, EStudentMain.eDeleteStudentFailMessage.getContent()));
	}

	private void validationRegisterStudent(StudentComponent studentsList, String message) throws RemoteException {
		Student student = new Student(message);
		if (!studentsList.isRegisteredStudent(student.studentId)) {
			studentsList.vStudent.add(student);
			eventBus.sendEvent(new Event(EventId.ClientOutput, EStudentMain.eRegisterStudentSuccessMessage.getContent()));
		} else eventBus.sendEvent(new Event(EventId.ClientOutput, EStudentMain.eRegisterStudentFailMessage.getContent()));
	}

	private void validationStudentNumberIsRegistered(StudentComponent studentsList, String inputStudentNumber) throws RemoteException {
		if (!studentsList.isRegisteredStudent(inputStudentNumber)) {
			validationError = EStudentMain.eTrue.getCheck();
			eventBus.sendEvent(new Event(EventId.ClientOutput, EStudentMain.eStudentNumberNotRegisteredMessage.getContent())); } }

	private void validationTakingCourse(StudentComponent studentsList, String inputCourseNumber, String inputStudentNumber) throws RemoteException {
		if ((validationError == EStudentMain.eFalse.getCheck()) && (studentsList.isAlreadyTakingCourse(inputCourseNumber, inputStudentNumber))) {
			validationError = EStudentMain.eTrue.getCheck();
			eventBus.sendEvent(new Event(EventId.ClientOutput, EStudentMain.eAlreadyTakingCourseMessage.getContent())); } }

	private void validationCompletedAdvancedCourses(StudentComponent studentsList
			, ArrayList<String> advancedCourseNumbers, String inputStudentNumber) throws RemoteException {
		if ((validationError == EStudentMain.eFalse.getCheck()) && (!studentsList.isCompletedAdvancedCourse(advancedCourseNumbers, inputStudentNumber))) {
			validationError = EStudentMain.eTrue.getCheck();
			eventBus.sendEvent(new Event(EventId.ClientOutput, EStudentMain.eNotCompletedAdvancedCourses.getContent())); } }

	private void validationTotalErrorCheck() throws RemoteException {
		if(validationError == EStudentMain.eFalse.getCheck()){
			eventBus.sendEvent(new Event(EventId.ClientOutput, applicationForCourse(studentsList, inputCourseNumber, inputStudentNumber))); } }
}
