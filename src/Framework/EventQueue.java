/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Framework;

import Components.Constant.Constants.EEventQueue;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

public class EventQueue implements Serializable {

    private static final long serialVersionUID = EEventQueue.eSerialVersionUID.getId(); // Default value for Serializable interface
    private Vector<Event> eventList;
	private long componentId;

	public EventQueue() {
		eventList = new Vector<Event> (EEventQueue.eInitialCapacity.getNumber(), EEventQueue.eCapacityIncrement.getNumber());
		componentId = Calendar.getInstance().getTimeInMillis(); }

	public long getId()	{
		return componentId;
	}
	public int getSize() {
		return eventList.size();
	}
	public void addEvent(Event newEvent) {
		eventList.add(newEvent);
	}
	public Event getEvent() {
		Event event = null;
		if (eventList.size() > EEventQueue.eZero.getNumber()) {
			event = eventList.get(EEventQueue.eZero.getNumber());
			eventList.removeElementAt(EEventQueue.eZero.getNumber()); }return event; }
	public void clearEventQueue() {
		eventList.removeAllElements();
	}
	@SuppressWarnings("unchecked")
	public EventQueue getCopy() {
		EventQueue eventQueue = new EventQueue();
		eventQueue.componentId = componentId;
		eventQueue.eventList = (Vector<Event>) eventList.clone();
		return eventQueue; }
}