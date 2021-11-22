/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Framework;

import Components.Constant.Constants.ERMIEventBusImpl;

import java.rmi.Naming;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class RMIEventBusImpl extends UnicastRemoteObject implements RMIEventBus {
    private static final long serialVersionUID = ERMIEventBusImpl.eSerialVersionUID.getId(); //Default value
    static Vector<EventQueue> eventQueueList;

	public RMIEventBusImpl() throws RemoteException {
		super();
		eventQueueList = new Vector<EventQueue>(ERMIEventBusImpl.eInitialCapacity.getNumber(), ERMIEventBusImpl.eCapacityIncrement.getNumber());
	}

	public static void main(String args[]) {
		try {
			RMIEventBusImpl eventBus = new RMIEventBusImpl();
	      		Naming.bind(ERMIEventBusImpl.eEventBus.getContent(), eventBus);
	      		System.out.println(ERMIEventBusImpl.eEventBusRunningNow.getContent());
		} catch (Exception e) {
			System.out.println(ERMIEventBusImpl.eEventBusRunningError.getContent() + e);
		}
	}
	
	synchronized public long register() throws RemoteException {
		EventQueue newEventQueue = new EventQueue();
		eventQueueList.add( newEventQueue );
		System.out.println(ERMIEventBusImpl.eRegisterMessage1.getContent()+ newEventQueue.getId() + ERMIEventBusImpl.eRegisterMessage2.getContent());
		return newEventQueue.getId();
	}
	
	synchronized public void unRegister(long id) throws RemoteException {
		EventQueue eventQueue;
		for ( int i = ERMIEventBusImpl.eZero.getNumber(); i < eventQueueList.size(); i++ ) {
			eventQueue =  eventQueueList.get(i);			
			if (eventQueue.getId() == id) {
				eventQueue = eventQueueList.remove(i);
				System.out.println(ERMIEventBusImpl.eUnRegisterMessage1.getContent()+ id + ERMIEventBusImpl.eUnRegisterMessage2.getContent());
			}
		}
	}
	
	synchronized public void sendEvent(Event sentEvent) throws RemoteException {
		EventQueue eventQueue;
		for ( int i = ERMIEventBusImpl.eZero.getNumber(); i < eventQueueList.size(); i++ ) {
			eventQueue = eventQueueList.get(i);
			System.out.println(ERMIEventBusImpl.eEventQueue.getContent()+eventQueue.getEvent());
			eventQueue.addEvent(sentEvent);
			eventQueueList.set(i, eventQueue);
		}
		System.out.println(ERMIEventBusImpl.eSendEventMessage1.getContent()+sentEvent.getEventId()
				+ERMIEventBusImpl.eSendEventMessage2.getContent()+sentEvent.getMessage()+ERMIEventBusImpl.eSendEventMessage3.getContent());
	}
	
	synchronized public EventQueue getEventQueue(long id) throws RemoteException {
		EventQueue originalQueue = null; 
		EventQueue copiedQueue =  null;
		for ( int i = ERMIEventBusImpl.eZero.getNumber(); i < eventQueueList.size(); i++ ) {
			originalQueue =  eventQueueList.get(i);
			if (originalQueue.getId() == id) {
				originalQueue = eventQueueList.get(i);
				copiedQueue = originalQueue.getCopy();
				originalQueue.clearEventQueue(); } }
		return copiedQueue;
	}
}