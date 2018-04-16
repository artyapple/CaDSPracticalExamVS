package cads.impl.app;

import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;

public class HorizontalGUIHandler implements IIDLCaDSEV3RMIMoveHorizontal {

	HorizontalMoveService moveService;
	
	HorizontalGUIHandler(HorizontalMoveService moveService) {
		this.moveService = moveService;
	}
	
	@Override
	public int getCurrentHorizontalPercent() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int moveHorizontalToPercent(int value, int arg1) throws Exception {
		moveService.move(value);// TODO Auto-generated method stub
		
		return 0;
	}

	@Override
	public int stop(int arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
