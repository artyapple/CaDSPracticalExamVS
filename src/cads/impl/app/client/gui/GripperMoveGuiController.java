package cads.impl.app.client.gui;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;

import cads.impl.hal.IGripperMotor;
import cads.impl.hal.client.GripperMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.Message;

public class GripperMoveGuiController implements IIDLCaDSEV3RMIMoveGripper {

	private IGripperMotor gripperMotor;
	
	public GripperMoveGuiController(IBuffer<Message> buffer) {
		this.gripperMotor = new GripperMotor(buffer);
	}

	@Override
	public int closeGripper(int arg0) throws Exception {
		gripperMotor.open(Boolean.FALSE);
		return 0;
	}

	@Override
	public int isGripperClosed() throws Exception {
		return 0;
	}

	@Override
	public int openGripper(int arg0) throws Exception {
		gripperMotor.open(Boolean.TRUE);
		return 0;
	}

}
