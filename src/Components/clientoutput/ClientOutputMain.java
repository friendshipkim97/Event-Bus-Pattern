/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.clientoutput;

import Components.constant.Constants;
import Components.constant.Constants.EClientOutputMain;
import framework.Event;
import framework.EventId;
import framework.EventQueue;
import framework.RMIEventBus;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientOutputMain {
	public static void main(String[] args) throws RemoteException, IOException, NotBoundException {
		RMIEventBus eventBusInterface = (RMIEventBus) Naming.lookup(EClientOutputMain.eEventBus.getContent());
		long componentId = eventBusInterface.register();
		System.out.println(EClientOutputMain.eClientOutputMainID.getContent() + componentId + EClientOutputMain.eClientOutputMainRegister.getContent());
		
		Event event = null;
		boolean done = EClientOutputMain.eFalse.getCheck();
		while (!done) {
			try {
				Thread.sleep(EClientOutputMain.eThreadTime.getNumber());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			EventQueue eventQueue = eventBusInterface.getEventQueue(componentId);
			for(int i = EClientOutputMain.eZero.getNumber(); i < eventQueue.getSize(); i++)  {
				event = eventQueue.getEvent();
				if (event.getEventId() == EventId.ClientOutput) {
					printOutput(event);
				} else if (event.getEventId() == EventId.QuitTheSystem) {
					//printLogReceive(event);
					eventBusInterface.unRegister(componentId);
					done = true;
				}
			}
		}
	}
	private static void printOutput(Event event) {
		System.out.println(event.getMessage());
	}
}
