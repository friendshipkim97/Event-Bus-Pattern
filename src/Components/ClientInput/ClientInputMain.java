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
		String userInput;
		String studentId;
		String studentFamilyName;
		String studentFirstName;
		String studentMajor;
		String manyCourseIds;
		System.out.println(EClientInputMain.eEnterStudentId.getContent());
		studentId = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationStudentId(studentId);
		userInput = studentId;

		System.out.println(EClientInputMain.eEnterFamilyName.getContent());
		studentFamilyName = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationStudentName(studentFamilyName);
		userInput += EClientInputMain.eSpace.getContent() + studentFamilyName;

		System.out.println(EClientInputMain.eEnterFirstName.getContent());
		studentFirstName = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationStudentName(studentFirstName);
		userInput += EClientInputMain.eSpace.getContent() + studentFirstName;

		System.out.println(EClientInputMain.eEnterDepartment.getContent());
		studentMajor = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationStudentMajor(studentMajor);
		userInput += EClientInputMain.eSpace.getContent() + studentMajor;

		System.out.println(EClientInputMain.eEnterCompletedCourses.getContent());
		System.out.println(EClientInputMain.eEnterCompletedCoursesEX.getContent());
		manyCourseIds = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationManyCourseIds(manyCourseIds);
		userInput += EClientInputMain.eSpace.getContent() + manyCourseIds;

		System.out.println(EClientInputMain.eMessage.getContent() + userInput + EClientInputMain.eEnter.getContent());
		return userInput;
	}
	private static String makeCourseInfo() throws IOException {
		String userInput;
		String courseId;
		String familyNameOfInstructor;
		String courseName;
		String manyCourseIds;

		System.out.println(EClientInputMain.eEnterCourseId.getContent());
		courseId = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationCourseId(courseId);
		userInput = courseId;

		System.out.println(EClientInputMain.eEnterFamilyNameOfInstructor.getContent());
		familyNameOfInstructor = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationFamilyNameOfInstructor(familyNameOfInstructor);
		userInput += EClientInputMain.eSpace.getContent() + familyNameOfInstructor;

		System.out.println(EClientInputMain.eEnterCourseNames.getContent());
		courseName = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationCourseName(courseName);
		userInput += EClientInputMain.eSpace.getContent() + courseName;

		System.out.println(EClientInputMain.eEnterCompletedCoursesId.getContent());
		System.out.println(EClientInputMain.eEnterCompletedCoursesIdEX.getContent());
		manyCourseIds = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationManyCourseIds(manyCourseIds);
		userInput += EClientInputMain.eSpace.getContent() + manyCourseIds;
		System.out.println(EClientInputMain.eMessage.getContent() + userInput + EClientInputMain.eEnter.getContent());
		return userInput;
	}
	//@SuppressWarnings("unused")
	private static String setStudentId() throws IOException {
		System.out.println(EClientInputMain.eSetStudentId.getContent());
		String studentId;
		studentId = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationStudentId(studentId);
		return studentId;
	}
	//@SuppressWarnings("unused")
	private static String setCourseId() throws IOException {
		System.out.println(EClientInputMain.eSetCourseId.getContent());
		String courseId;
		courseId = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationCourseId(courseId);
		return courseId;
	}
	//@SuppressWarnings("unused")
	private static String setCourseIdAndStudentId() throws IOException {
		System.out.println(EClientInputMain.eSetStudentIdAndCourseId.getContent());
		String courseIdAndStudentId;
		courseIdAndStudentId = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		validationCourseIdAndStudentId(courseIdAndStudentId);
		return courseIdAndStudentId;
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

	/**
	 * validation
	 */
	private static void validationStudentName(String studentName) {
		if(studentName.matches(EClientInputMain.eMatchContainNumber.getContent()) ||
				studentName.equals(EClientInputMain.eEmpty.getContent())){
			throw new IllegalArgumentException(EClientInputMain.eStudentNameMatchMessage.getContent()); } }
	private static void validationStudentId(String studentId) {
		if((studentId.matches(EClientInputMain.eMatchOnlyNumber.getContent()) == false) ||
				studentId.equals(EClientInputMain.eEmpty.getContent())){
			throw new IllegalArgumentException(EClientInputMain.eStudentNumberMatchMessage.getContent()); } }
	private static void validationStudentMajor(String studentMajor) {
		if(studentMajor.matches(EClientInputMain.eMatchContainNumber.getContent()) ||
				studentMajor.equals(EClientInputMain.eEmpty.getContent())){
			throw new IllegalArgumentException(EClientInputMain.eMajorMatchMessage.getContent()); } }
	private static void validationManyCourseIds(String courseIds) {
		if(courseIds.matches(EClientInputMain.eMatchManyCourseNumbers.getContent()) == false){
			throw new IllegalArgumentException(EClientInputMain.eManyCourseNumbersMatchMessage.getContent()); } }
	private static void validationCourseId(String courseId) {
		if(courseId.matches(EClientInputMain.eMatchOnlyNumber.getContent()) == false ||
				courseId.equals(EClientInputMain.eEmpty.getContent())){
			throw new IllegalArgumentException(EClientInputMain.eCourseNumberMatchMessage.getContent()); } }
	private static void validationFamilyNameOfInstructor(String familyNameOfInstructor) {
		if(familyNameOfInstructor.matches(EClientInputMain.eMatchContainNumber.getContent()) ||
				familyNameOfInstructor.equals(EClientInputMain.eEmpty.getContent())){
			throw new IllegalArgumentException(EClientInputMain.eProfessorLastNameMatchMessage.getContent()); } }
	private static void validationCourseName(String courseName) {
		if(courseName.matches(EClientInputMain.eMatchContainNumber.getContent())){
			throw new IllegalArgumentException(EClientInputMain.eCourseNameMatchMessage.getContent()); } }
	private static void validationCourseIdAndStudentId(String courseIdAndStudentId) {
		if(courseIdAndStudentId.matches(EClientInputMain.eMatchCourseIdAndStudentId.getContent()) == false){
			throw new IllegalArgumentException(EClientInputMain.eCourseIdAndStudentIdMatchMessage.getContent()); } }
}
