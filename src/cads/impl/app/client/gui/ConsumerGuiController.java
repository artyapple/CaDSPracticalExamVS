package cads.impl.app.client.gui;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;

import cads.impl.factory.Factory;

public class ConsumerGuiController implements ICaDSRMIConsumer {

	private RobotManager manager;

	public ConsumerGuiController() throws InstantiationException, IllegalAccessException {
		manager = Factory.current().getInstance(RobotManager.class);
	}

	@Override
	public void register(ICaDSRobotGUIUpdater arg0) {

	}

	@Override
	public void update(String arg0) {
		String value = arg0.replace("Item: ", "");
		manager.setCurrent(value);
	}
}

