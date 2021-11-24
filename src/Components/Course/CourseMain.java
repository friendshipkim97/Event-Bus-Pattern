/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.Course;

import Components.Constant.Constants;
import Components.Constant.Constants.ECourseMain;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class CourseMain {

	public static void main(String[] args) throws IOException, NotBoundException {
		new CourseMain();
	}

	RMIEventBus eventBus;
	long componentId;
	CourseComponent coursesList;
	boolean validationError;

	public CourseMain() throws IOException, NotBoundException {
		registerComponent();
        readCourseFile();
        setEvent();
	}

	private void readCourseFile() throws IOException {
		coursesList = new CourseComponent(ECourseMain.eCourseFileName.getContent()); }

	private void registerComponent() throws RemoteException, NotBoundException, MalformedURLException {
		eventBus = (RMIEventBus) Naming.lookup(ECourseMain.eEventBus.getContent());
		componentId = eventBus.register();
		System.out.println(ECourseMain.eCourseMainID.getContent() + componentId + ECourseMain.eCourseMainRegister.getContent()); }

	private void setEvent() throws RemoteException {
		Event event = null;
		boolean done = ECourseMain.eFalse.getCheck();
		while (!done) {
			try {
				Thread.sleep(ECourseMain.eThreadTime.getNumber());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBus.getEventQueue(componentId);
			for (int i = ECourseMain.eZero.getNumber(); i < eventQueue.getSize(); i++) {
				event = eventQueue.getEvent();
				switch (event.getEventId()) {
					case ListCourses:
						printLogEvent(ECourseMain.eEventGet.getContent(), event);
						eventBus.sendEvent(new Event(EventId.ClientOutput, makeCourseList(coursesList)));
						break;
					case RegisterCourses:
						printLogEvent(ECourseMain.eEventGet.getContent(), event);
						validationRegisterCourse(coursesList, event.getMessage());
						break;
					case DeleteCourses:
						printLogEvent(ECourseMain.eEventDelete.getContent(), event);
						validationDeleteCourse(coursesList, event.getMessage());
						break;
					case applicationForCourse:
						validationError = Constants.EStudentMain.eFalse.getCheck();
						printLogEvent(ECourseMain.eEventApplicationForCourse.getContent(), event);
						validationCourseNumber(coursesList, event.getMessage());
						validationTotalErrorCheck(coursesList, event.getMessage());
						break;
					case QuitTheSystem:
						eventBus.unRegister(componentId);
						done = ECourseMain.eTrue.getCheck();
						break;
					default:
						break;
				}
			}
		}
	}

	private String makeCourseList(CourseComponent coursesList) {
		String returnString = ECourseMain.eEmpty.getContent();
		for (int j = ECourseMain.eZero.getNumber(); j < coursesList.vCourse.size(); j++) {
			returnString += coursesList.getCourseList().get(j).getString() + ECourseMain.eEnter.getContent(); }
		return returnString; }

	private void printLogEvent(String comment, Event event) {
		System.out.println(
				ECourseMain.eLogEventMessage1.getContent() + comment + ECourseMain.eLogEventMessage2.getContent()
						+ event.getEventId() + ECourseMain.eLogEventMessage3.getContent() + event.getMessage()); }

	private String makeCourseListWithAdvancedCoursesMessage(CourseComponent coursesList, String message) {
		String[] distinguishedMessage = message.split(ECourseMain.eMessageSplitFormat.getContent());
		String returnString = ECourseMain.eEmpty.getContent();
		returnString += message;
		for (String advancedCourse : coursesList.getCourse(distinguishedMessage[ECourseMain.eZero.getNumber()]).getPrerequisiteCoursesList()) {
			returnString += ECourseMain.eSpace.getContent() + advancedCourse; }
		return returnString; }

	/**
	 * validation
	 */

	private void validationDeleteCourse(CourseComponent coursesList, String message) throws RemoteException {
		if (coursesList.isRegisteredCourse(message)) {
			coursesList.deleteCourse(message);
			eventBus.sendEvent(new Event(EventId.ClientOutput, ECourseMain.eDeleteCourseSuccessMessage.getContent()));
		} else eventBus.sendEvent(new Event(EventId.ClientOutput, ECourseMain.eDeleteCourseFailMessage.getContent())); }

	private void validationRegisterCourse(CourseComponent coursesList, String message) throws RemoteException {
		Course course = new Course(message);
		if (!coursesList.isRegisteredCourse(course.courseId)) {
			coursesList.vCourse.add(course);
			eventBus.sendEvent(new Event(EventId.ClientOutput, ECourseMain.eRegisterCourseSuccessMessage.getContent()));
		} else eventBus.sendEvent(new Event(EventId.ClientOutput, ECourseMain.eRegisterCourseFailMessage.getContent())); }

	private void validationCourseNumber(CourseComponent coursesList, String message) throws RemoteException {
		String[] distinguishedMessage = message.split(ECourseMain.eMessageSplitFormat.getContent());
		if (!coursesList.isRegisteredCourse(distinguishedMessage[ECourseMain.eZero.getNumber()])) {
			validationError = ECourseMain.eTrue.getCheck();
			eventBus.sendEvent(new Event(EventId.ClientOutput, ECourseMain.eValidationCourseNumberMessage.getContent())); } }
	private void validationTotalErrorCheck(CourseComponent coursesList, String message) throws RemoteException {
		if(validationError == ECourseMain.eFalse.getCheck()){
			eventBus.sendEvent(new Event(EventId.validationApplicationForCourse, makeCourseListWithAdvancedCoursesMessage(coursesList, message))); } }
}
