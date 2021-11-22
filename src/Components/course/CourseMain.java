/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */
package Components.course;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import Components.constant.Constants.ECourseMain;
import framework.Event;
import framework.EventId;
import framework.EventQueue;
import framework.RMIEventBus;

public class CourseMain {
	public static void main(String[] args) throws FileNotFoundException, IOException, NotBoundException {
		RMIEventBus eventBus = (RMIEventBus) Naming.lookup(ECourseMain.eEventBus.getContent());
		long componentId = eventBus.register();
		System.out.println(ECourseMain.eCourseMainID.getContent() + componentId + ECourseMain.eCourseMainRegister.getContent());

		CourseComponent coursesList = new CourseComponent(ECourseMain.eCourseFileName.getContent());
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
					eventBus.sendEvent(new Event(EventId.ClientOutput, registerCourse(coursesList, event.getMessage())));
					break;
				case DeleteCourses:
					printLogEvent(ECourseMain.eEventDelete.getContent(), event);
					eventBus.sendEvent(new Event(EventId.ClientOutput, deleteCourse(coursesList, event.getMessage())));
					break;
				case applicationForCourse:
					printLogEvent(ECourseMain.eEventApplicationForCourse.getContent(), event);
					String validationCourseNumber = validationCourseNumber(coursesList, event.getMessage());
					if(!validationCourseNumber.isEmpty())
					  eventBus.sendEvent(new Event(EventId.ClientOutput, validationCourseNumber));
					else
					  eventBus.sendEvent(new Event(EventId.validationApplicationForCourse,
							  makeCourseListWithAdvancedCoursesMessage(coursesList, event.getMessage())));
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

	private static String makeCourseList(CourseComponent coursesList) {
		String returnString = ECourseMain.eEmpty.getContent();
		for (int j = ECourseMain.eZero.getNumber(); j < coursesList.vCourse.size(); j++) {
			returnString += coursesList.getCourseList().get(j).getString() + ECourseMain.eEnter.getContent(); }
		return returnString; }

	private static void printLogEvent(String comment, Event event) {
		System.out.println(
				ECourseMain.eLogEventMessage1.getContent() + comment + ECourseMain.eLogEventMessage2.getContent()
						+ event.getEventId() + ECourseMain.eLogEventMessage3.getContent() + event.getMessage()); }

	private static String makeCourseListWithAdvancedCoursesMessage(CourseComponent coursesList, String message) {
		String[] distinguishedMessage = message.split(ECourseMain.eMessageSplitFormat.getContent());
		String returnString = ECourseMain.eEmpty.getContent();
		returnString += message;
		for (String advancedCourse : coursesList.getCourse(distinguishedMessage[ECourseMain.eZero.getNumber()]).getPrerequisiteCoursesList()) {
			returnString += ECourseMain.eSpace.getContent() + advancedCourse; }
		return returnString;
	}

	/**
	 * validation
	 */
	private static String deleteCourse(CourseComponent coursesList, String message) {
        if (coursesList.isRegisteredCourse(message)) {
			coursesList.deleteCourse(message);
			return ECourseMain.eDeleteCourseSuccessMessage.getContent();
		} else return ECourseMain.eDeleteCourseFailMessage.getContent(); }
	
	private static String registerCourse(CourseComponent coursesList, String message) {
		Course course = new Course(message);
		if (!coursesList.isRegisteredCourse(course.courseId)) {
			coursesList.vCourse.add(course);
			return ECourseMain.eRegisterCourseSuccessMessage.getContent();
		} else return ECourseMain.eRegisterCourseFailMessage.getContent(); }

	private static String validationCourseNumber(CourseComponent coursesList, String message) {
		String[] distinguishedMessage = message.split(ECourseMain.eMessageSplitFormat.getContent());
		if(!coursesList.isRegisteredCourse(distinguishedMessage[ECourseMain.eZero.getNumber()])){
			return ECourseMain.eValidationCourseNumberMessage.getContent(); }
		return ECourseMain.eEmpty.getContent(); }
}
