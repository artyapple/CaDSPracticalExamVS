package cads.impl.app.server.listener;

import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.json.simple.JSONObject;

public class FeedBackListener implements ICaDSEV3RobotFeedBackListener {

	@Override
	public void giveFeedbackByJSonTo(JSONObject arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0);
	}
}
