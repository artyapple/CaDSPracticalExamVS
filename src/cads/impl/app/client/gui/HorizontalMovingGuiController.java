package cads.impl.app.client.gui;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;

import cads.impl.app.client.service.ServiceHorizontalMotor;
import cads.impl.mom.Message;
import cads.impl.mom.MessageBuffer;

public class HorizontalMovingGuiController implements IIDLCaDSEV3RMIMoveHorizontal {

	private ServiceHorizontalMotor horizontalMotorService;
	
	public HorizontalMovingGuiController(MessageBuffer<Message> horizontalMessageBuffer) {
		horizontalMotorService = new ServiceHorizontalMotor(horizontalMessageBuffer);
	}

	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveHorizontalToPercent(int transactionID, int percent) throws Exception {
		horizontalMotorService.move(percent);
		return 0;
	}

	@Override
	public int stop(int transactionID) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
