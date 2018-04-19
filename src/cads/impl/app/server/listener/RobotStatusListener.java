package cads.impl.app.server.listener;

import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.json.simple.JSONObject;

import cads.impl.mom.MarshallingService;

public class RobotStatusListener implements ICaDSEV3RobotStatusListener {

	private static final String STATE = "state";
	private MarshallingService ms;
	
	@Override
	public void onStatusMessage(JSONObject json) {
		
		json.toJSONString();
		String state = (String) json.get("state");
	}

}
