package cads.impl.app.client.gui;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;

import cads.impl.factory.Factory;
import cads.impl.hal.IGripperMotor;

public class GripperMoveGuiController implements IIDLCaDSEV3RMIMoveGripper {

	private Factory factory;
	private RobotManager manager;

	public GripperMoveGuiController() throws InstantiationException, IllegalAccessException {
		factory = Factory.current();
		manager = factory.getInstance(RobotManager.class);
	}

	@Override
	public int closeGripper(int transactionID) throws Exception {
		IGripperMotor gripper = getCurrent();
		gripper.grab(true);
		System.out.println("close.... TID: " + transactionID);
		return 0;
	}

	@Override
	public int openGripper(int transactionID) throws Exception {
		IGripperMotor gripper = getCurrent();
		gripper.grab(false);
		System.out.println("open.... TID: " + transactionID);
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		return 0;
	}
	
	private IGripperMotor getCurrent() throws InstantiationException, IllegalAccessException{
		System.out.println("IGripperMotor" + manager.getCurrent());
		return factory.getInstance("IGripperMotor" + manager.getCurrent());
	}
}
