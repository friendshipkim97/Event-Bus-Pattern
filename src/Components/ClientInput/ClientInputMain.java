/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.ClientInput;

import Components.Constant.Constants.EClientInputMain;
import Framework.Event;
import Framework.EventId;
import Framework.RMIEventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientInputMain {

	public static void main(String[] args) throws IOException, NotBoundException {
		new ClientInputMain();
	}

	RMIEventBus eventBus;
	long componentId;

	public ClientInputMain() throws RemoteException, NotBoundException, MalformedURLException {
		registerComponent();
		setEvent();
	}

	private void setEvent() {
		boolean done = EClientInputMain.eFalse.getCheck();
		while (!done) {
			writeMenu();
			try {
				switch (new BufferedReader(new InputStreamReader(System.in)).readLine().trim()) {
					case "1":
						eventBus.sendEvent(new Event(EventId.ListStudents, null));
						printLogSend(EventId.ListStudents);
						break;
					case "2":
						eventBus.sendEvent(new Event(EventId.ListCourses, null));
						printLogSend(EventId.ListCourses);
						break;
					case "3":
						eventBus.sendEvent(new Event(EventId.RegisterStudents, makeStudentInfo()));
						printLogSend(EventId.RegisterStudents);
						break;
					case "4":
						eventBus.sendEvent(new Event(EventId.RegisterCourses, makeCourseInfo()));
						printLogSend(EventId.RegisterCourses);
						break;
					case "5":
						eventBus.sendEvent(new Event(EventId.DeleteStudents, setStudentId()));
						printLogSend(EventId.DeleteStudents);
						break;
					case "6":
						eventBus.sendEvent(new Event(EventId.DeleteCourses, setCourseId()));
						printLogSend(EventId.DeleteCourses);
						break;
					case "7":
						eventBus.sendEvent(new Event(EventId.applicationForCourse, setCourseIdAndStudentId()));
						printLogSend(EventId.applicationForCourse);
						break;
					case "8":
						eventBus.sendEvent(new Event(EventId.QuitTheSystem, EClientInputMain.eQuitTheSystem.getContent()));
						printLogSend(EventId.QuitTheSystem);
						eventBus.unRegister(componentId);
						done = EClientInputMain.eTrue.getCheck();
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

	private void registerComponent() throws RemoteException, NotBoundException, MalformedURLException {
		eventBus = (RMIEventBus) Naming.lookup(EClientInputMain.eEventBus.getContent());
		componentId = eventBus.register();
		System.out.println(EClientInputMain.eClientInputMainID.getContent() + componentId + EClientInputMain.eClientInputMainRegister.getContent());
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
