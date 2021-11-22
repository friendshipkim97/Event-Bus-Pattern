/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.clientinput;

import Components.constant.Constants.EClientInputMain;
import framework.Event;
import framework.EventId;
import framework.RMIEventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;



public class ClientInputMain {
	public static void main(String[] args) throws IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup(EClientInputMain.eEventBus.getContent());
		long componentId = eventBus.register();
		System.out.println(EClientInputMain.eClientInputMainID.getContent() + componentId + EClientInputMain.eClientInputMainRegister.getContent());
		
		boolean done = EClientInputMain.eFalse.getCheck();
		while (!done) {
			writeMenu();

			EClientInputMain sChoice = EClientInputMain.valueOf(new BufferedReader(new InputStreamReader(System.in)).readLine().trim());
			try {
				switch (sChoice) {
				case one:
					eventBus.sendEvent(new Event(EventId.ListStudents, null));
					printLogSend(EventId.ListStudents);
					break;
				case two:
					eventBus.sendEvent(new Event(EventId.ListCourses, null));
					printLogSend(EventId.ListCourses);
					break;
				case three:
					eventBus.sendEvent(new Event(EventId.RegisterStudents, makeStudentInfo()));
					printLogSend(EventId.RegisterStudents);
					break;
				case four:
					eventBus.sendEvent(new Event(EventId.RegisterCourses, makeCourseInfo()));
					printLogSend(EventId.RegisterCourses);
					break;
				case five:
					eventBus.sendEvent(new Event(EventId.DeleteStudents, setStudentId()));
					printLogSend(EventId.DeleteStudents);
					break;
				case six:
					eventBus.sendEvent(new Event(EventId.DeleteCourses, setCourseId()));
					printLogSend(EventId.DeleteCourses);
					break;
				case seven:
					eventBus.sendEvent(new Event(EventId.applicationForCourse, setCourseIdAndStudentId()));
					printLogSend(EventId.applicationForCourse);
					break;
				case eight:
					eventBus.sendEvent(new Event(EventId.QuitTheSystem, EClientInputMain.eQuitTheSystem.getContent()));
					printLogSend(EventId.QuitTheSystem);
					eventBus.unRegister(componentId);
					done =  EClientInputMain.eTrue.getCheck();
					break;
				default:
					System.out.println(EClientInputMain.ePleaseChooseTheRightMenu.getContent());
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static String makeStudentInfo() throws IOException {
		String userInput = EClientInputMain.eEmpty.getContent();
		System.out.println(EClientInputMain.eEnterStudentId.getContent());
		userInput = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(EClientInputMain.eEnterFamilyName.getContent());
		userInput += EClientInputMain.eSpace.getContent() + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(EClientInputMain.eEnterFirstName.getContent());
		userInput += EClientInputMain.eSpace.getContent() + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(EClientInputMain.eEnterDepartment.getContent());
		userInput += EClientInputMain.eSpace.getContent() + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(
				EClientInputMain.eEnterCompletedCourses.getContent());
		System.out.println(EClientInputMain.eEnterCompletedCoursesEX.getContent());
		userInput += EClientInputMain.eSpace.getContent() + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(EClientInputMain.eMessage.getContent() + userInput + EClientInputMain.eEnter.getContent());
		return userInput;
	}
	private static String makeCourseInfo() throws IOException {
		String userInput = EClientInputMain.eEmpty.getContent();
		System.out.println(EClientInputMain.eEnterCourseId.getContent());
		userInput = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(EClientInputMain.eEnterFamilyNameOfInstructor.getContent());
		userInput += EClientInputMain.eSpace.getContent() + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(
				EClientInputMain.eEnterCourseNames.getContent());
		userInput += EClientInputMain.eSpace.getContent() + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(
				EClientInputMain.eEnterCompletedCoursesId.getContent());
		System.out.println(EClientInputMain.eEnterCompletedCoursesIdEX.getContent());
		userInput += EClientInputMain.eSpace.getContent() + new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		System.out.println(EClientInputMain.eMessage.getContent() + userInput + EClientInputMain.eEnter.getContent());
		return userInput;
	}
	//@SuppressWarnings("unused")
	private static String setStudentId() throws IOException {
		System.out.println(EClientInputMain.eSetStudentId.getContent());
		return new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
	}
	//@SuppressWarnings("unused")
	private static String setCourseId() throws IOException {
		System.out.println(EClientInputMain.eSetCourseId.getContent());
		return new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
	}
	//@SuppressWarnings("unused")
	private static String setCourseIdAndStudentId() throws IOException {
		System.out.println(EClientInputMain.eSetStudentIdAndCourseId.getContent());
		return new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
	}
	private static void writeMenu() {
		System.out.println(EClientInputMain.eMenuOne.getContent());
		System.out.println(EClientInputMain.eMenuTwo.getContent());
		System.out.println(EClientInputMain.eMenuThree.getContent());
		System.out.println(EClientInputMain.eMenuFour.getContent());
		System.out.println(EClientInputMain.eMenuFive.getContent());
		System.out.println(EClientInputMain.eMenuSix.getContent());
		System.out.println(EClientInputMain.eMenuSeven.getContent());
		System.out.println(EClientInputMain.eMenuEight.getContent());
		System.out.print(EClientInputMain.eMenuChooseNumber.getContent());
	}
	private static void printLogSend(EventId eventId) {
		System.out.println(EClientInputMain.eSendEventMessage.getContent() + eventId + EClientInputMain.eSendEventMessageEnter.getContent());
	}
}
