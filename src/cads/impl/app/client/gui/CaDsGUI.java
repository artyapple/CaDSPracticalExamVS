package cads.impl.app.client.gui;

import org.cads.ev3.gui.ICaDSRobotGUIUpdater;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;

import cads.impl.factory.Factory;
import cads.impl.hal.IGripperMotor;
import cads.impl.hal.IHorizontalMotor;
import cads.impl.hal.IVertikalMotor;

public class CaDsGUI implements ICaDSRMIConsumer, IIDLCaDSEV3RMIMoveGripper, IIDLCaDSEV3RMIMoveHorizontal, IIDLCaDSEV3RMIMoveVertical {

	private Factory factory;
	private RobotManager manager;

	public CaDsGUI() throws InstantiationException, IllegalAccessException {
		factory = Factory.current();
		manager = factory.getInstance(RobotManager.class);
	}

	@Override
	public void register(ICaDSRobotGUIUpdater arg0) {

	}

	@Override
	public void update(String arg0) {
		String value = arg0.replace("Item: ", "");
		int th = manager.getHorPos();
		int tv = manager.getVertPos();	
		manager.setCurrent(value);
		
		try {
			moveVerticalToPercent(0, tv);
			moveHorizontalToPercent(0, th);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int closeGripper(int transactionID) throws Exception {
		IGripperMotor gripper = getCurrent();
		gripper.grab(true);
		//System.out.println("close.... TID: " + transactionID);
		return 0;
	}

	@Override
	public int openGripper(int transactionID) throws Exception {
		IGripperMotor gripper = getCurrent();
		gripper.grab(false);
		//System.out.println("open.... TID: " + transactionID);
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		return 0;
	}
	
	private IGripperMotor getCurrent() throws InstantiationException, IllegalAccessException{
		//System.out.println("IGripperMotor" + manager.getCurrent());
		return factory.getInstance("IGripperMotor" + manager.getCurrent());
	}
	
	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		return 0;
	}

	@Override
	public int moveHorizontalToPercent(int transactionID, int percent) throws Exception {
        //System.out.println("Call to move horizontal -  TID: " + transactionID + " degree " + percent);
        IHorizontalMotor horizontalMotor = getCurrentH();
        horizontalMotor.move(percent);
		return 0;
	}

	@Override
	public int stop(int transactionID) throws Exception {
		//System.out.println("Stop movement.... TID: " + transactionID);
		return 0;
	}
	
	private IHorizontalMotor getCurrentH() throws InstantiationException, IllegalAccessException{
		return factory.getInstance("IHorizontalMotor" + manager.getCurrent());							
	}
	
	@Override
	public int getCurrentVerticalPercent() throws Exception {
		return 0;
	}

	@Override
	public int moveVerticalToPercent(int transactionID, int percent) throws Exception {
        //System.out.println("Call to move vertical -  TID: " + transactionID + " degree " + percent);
        IVertikalMotor verticalMotor = getCurrentV();
        verticalMotor.move(percent);
		return 0;
	}

	private IVertikalMotor getCurrentV() throws InstantiationException, IllegalAccessException{
		return factory.getInstance("IVertikalMotor" + manager.getCurrent());
	}
}
