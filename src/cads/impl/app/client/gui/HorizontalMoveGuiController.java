package cads.impl.app.client.gui;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;

import cads.impl.factory.Factory;
import cads.impl.hal.IHorizontalMotor;

public class HorizontalMoveGuiController implements IIDLCaDSEV3RMIMoveHorizontal {

	private Factory factory;
	private RobotManager manager;
	
	public HorizontalMoveGuiController() throws InstantiationException, IllegalAccessException {
		factory = Factory.current();
		manager = factory.getInstance(RobotManager.class);
	}
	
	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		return 0;
	}

	@Override
	public int moveHorizontalToPercent(int transactionID, int percent) throws Exception {
        System.out.println("Call to move horizontal -  TID: " + transactionID + " degree " + percent);
        IHorizontalMotor horizontalMotor = getCurrent();
        horizontalMotor.move(percent);
		return 0;
	}

	@Override
	public int stop(int transactionID) throws Exception {
		System.out.println("Stop movement.... TID: " + transactionID);
		return 0;
	}
	
	private IHorizontalMotor getCurrent() throws InstantiationException, IllegalAccessException{
		return factory.getInstance("IHorizontalMotor" + manager.getCurrent());				
				
	}

}
