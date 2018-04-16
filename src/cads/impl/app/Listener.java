package cads.impl.app;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

public class Listener implements Runnable, ICaDSEV3RobotStatusListener, ICaDSEV3RobotFeedBackListener {

	CaDSEV3RobotHAL caller;
	CaDSEV3RobotType robotType;
	
	public Listener(CaDSEV3RobotHAL caller, CaDSEV3RobotType robotType) {
		this.caller = caller;
		this.robotType = robotType;
	}
	
	@Override
	public synchronized void giveFeedbackByJSonTo(JSONObject feedback) {
		System.out.println(feedback);
	}
	
	@Override
	public synchronized void onStatusMessage(JSONObject status) {
		System.out.println(status);
	}

	@Override
	public void run() {
		try {
			caller = CaDSEV3RobotHAL.createInstance(robotType, this, this);
			while(!Thread.currentThread().isInterrupted()) {
				// waiting for Instructions
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.exit(0);
	}
}
