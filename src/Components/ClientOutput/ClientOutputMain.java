/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in MyungJi University 
 */

package Components.ClientOutput;

import Components.Constant.Constants.EClientOutputMain;
import Framework.Event;
import Framework.EventId;
import Framework.EventQueue;
import Framework.RMIEventBus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientOutputMain {

	public static void main(String[] args) throws IOException, NotBoundException {
		new ClientOutputMain();
	}

	RMIEventBus eventBusInterface;
	long componentId;

	public ClientOutputMain() throws RemoteException, NotBoundException, MalformedURLException {
		registerComponent();
		setEvent();
	}

	private void setEvent() throws RemoteException {
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
	private void registerComponent() throws RemoteException, NotBoundException, MalformedURLException {
		eventBusInterface = (RMIEventBus) Naming.lookup(EClientOutputMain.eEventBus.getContent());
		componentId = eventBusInterface.register();
		System.out.println(EClientOutputMain.eClientOutputMainID.getContent() + componentId + EClientOutputMain.eClientOutputMainRegister.getContent());
	}
	private static void printOutput(Event event) {
		System.out.println(event.getMessage());
	}
}
