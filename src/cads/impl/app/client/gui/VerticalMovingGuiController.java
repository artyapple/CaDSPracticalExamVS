package cads.impl.app.client.gui;

import java.net.SocketException;
import java.net.UnknownHostException;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;

import cads.impl.app.client.service.ServiceVertikalMotor;
import cads.impl.hal.client.VertikalMotor;
import cads.impl.mom.MessageBuffer;

/**
 *  Controller for handling of GUI events of vertical moving.
 */
public class VerticalMovingGuiController implements IIDLCaDSEV3RMIMoveVertical {

	private ServiceVertikalMotor verticalMotorService;
	
	public VerticalMovingGuiController(MessageBuffer buffer) throws InstantiationException, IllegalAccessException, SocketException, UnknownHostException {
		verticalMotorService = new ServiceVertikalMotor(buffer);
	}
	
	@Override
	public int getCurrentVerticalPercent() throws Exception {
		return 0;
	}

	@Override
	public int moveVerticalToPercent(int transactionID, int percent) throws Exception {
		verticalMotorService.move(percent);
		return 0;
	}

	@Override
	public int stop(int transactionID) throws Exception {
		return 0;
	}
}
