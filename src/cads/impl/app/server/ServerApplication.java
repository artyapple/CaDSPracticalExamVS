package cads.impl.app.server;

import java.io.IOException;
import java.net.InetAddress;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.CaDSEV3Robot;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cads.impl.app.server.listener.FeedBackListener;
import cads.impl.app.server.listener.RobotStatusListener;
import cads.impl.app.server.listener.RobotStatusListener.ValueToObserve;
import cads.impl.app.server.listener.Status;
import cads.impl.hal.IVertikalMotor;
import cads.impl.hal.server.HorizontalMotor;
import cads.impl.hal.server.VertikalMotor;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.Watchdog;
import cads.impl.mom.Watchdog.Type;

public class ServerApplication {
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		 RobotStatusListener statusListener = new RobotStatusListener();
		 CaDSEV3RobotHAL robot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, statusListener, new FeedBackListener());
		 
		 VertikalMotor vmotor = new VertikalMotor();
		 statusListener.subscribe(ValueToObserve.VERTIKAL, vmotor);
		 HorizontalMotor hmotor = new HorizontalMotor();
		 statusListener.subscribe(ValueToObserve.HORIZONTAL, hmotor);
		 
		 // Watchdog
		 Watchdog watchdog;
		 watchdog = new Watchdog(InetAddress.getByName("localhost"), 8088, Type.SERVER);
		 Thread watchdogThread = new Thread(watchdog);
		 watchdogThread.start();
		 
		 // Testinstructions
		 
//		 vmotor.move(50);
//		 vmotor.move(0);
//		 vmotor.move(100);
//		 hmotor.move(100);
//		 vmotor.move(0);
//		 hmotor.move(50);

	}
}
