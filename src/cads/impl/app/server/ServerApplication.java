package cads.impl.app.server;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.CaDSEV3Robot;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;

public class ServerApplication {
	
	private static CaDSEV3RobotHAL robot = null;
	
	public static void main(String[] args) {
		
		ICaDSEV3RobotStatusListener statusListener;
		ICaDSEV3RobotFeedBackListener feedbackListener;
		robot = new CaDSEV3RobotHAL(CaDSEV3RobotType.SIMULATION, statusListener, feedbackListener);
	}
	
}

