package cads.impl.app.client.gui;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;

import cads.impl.factory.Factory;
import cads.impl.hal.IVertikalMotor;

/**
 *  Controller for handling of GUI events of vertical moving.
 */
public class VerticalMoveGuiController implements IIDLCaDSEV3RMIMoveVertical {

	private Factory factory;
	private RobotManager manager;
	
	public VerticalMoveGuiController() throws InstantiationException, IllegalAccessException {
		factory = Factory.current();
		manager = factory.getInstance(RobotManager.class);
	}
	
	@Override
	public int getCurrentVerticalPercent() throws Exception {
		return 0;
	}

	@Override
	public int moveVerticalToPercent(int transactionID, int percent) throws Exception {
        System.out.println("Call to move vertical -  TID: " + transactionID + " degree " + percent);
        IVertikalMotor verticalMotor = getCurrent();
        verticalMotor.move(percent);
		return 0;
	}

	@Override
	public int stop(int transactionID) throws Exception {
		System.out.println("Stop movement.... TID: " + transactionID);
		// must be handled? The current GUI has no stop button
		return 0;
	}
	
	private IVertikalMotor getCurrent() throws InstantiationException, IllegalAccessException{
		return factory.getInstance("IVertikalMotor" + manager.getCurrent());
	}
}
