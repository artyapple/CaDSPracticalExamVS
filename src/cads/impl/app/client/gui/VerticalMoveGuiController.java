package cads.impl.app.client.gui;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;

import cads.impl.hal.IVertikalMotor;
import cads.impl.hal.client.VertikalMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.Message;

/**
 *  Controller for handling of GUI events of vertical moving.
 */
public class VerticalMoveGuiController implements IIDLCaDSEV3RMIMoveVertical {

	private IVertikalMotor vertikalMotor;
	
	public VerticalMoveGuiController(IBuffer<Message> buffer) {
		vertikalMotor = new VertikalMotor(buffer);
	}
	
	@Override
	public int getCurrentVerticalPercent() throws Exception {
		return 0;
	}

	@Override
	public int moveVerticalToPercent(int transactionID, int percent) throws Exception {
		vertikalMotor.move(percent);
		return 0;
	}

	@Override
	public int stop(int transactionID) throws Exception {
		return 0;
	}
}
