package cads.impl.app.client.gui;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;

import cads.impl.hal.IHorizontalMotor;
import cads.impl.hal.client.HorizontalMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.Message;

public class HorizontalMoveGuiController implements IIDLCaDSEV3RMIMoveHorizontal {

	private IHorizontalMotor horizontalMotor;
	
	public HorizontalMoveGuiController(IBuffer<Message> horizontalMessageBuffer) {
		this.horizontalMotor = new HorizontalMotor(horizontalMessageBuffer);
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		return 0;
	}

	@Override
	public int moveHorizontalToPercent(int transactionID, int percent) throws Exception {
		horizontalMotor.move(percent);
		return 0;
	}

	@Override
	public int stop(int transactionID) throws Exception {
		return 0;
	}

}
