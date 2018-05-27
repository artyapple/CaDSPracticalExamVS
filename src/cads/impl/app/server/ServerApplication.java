package cads.impl.app.server;

import java.io.IOException;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cads.impl.app.server.controller.GripperRobotController;
import cads.impl.app.server.controller.HorizontalRobotController;
import cads.impl.app.server.controller.RobotController;
import cads.impl.app.server.controller.VertikalRobotController;
import cads.impl.app.server.listener.FeedBackListener;
import cads.impl.app.server.listener.RobotStatusListener;
import cads.impl.app.server.listener.RobotStatusListener.ValueToObserve;
import cads.impl.hal.server.GripperMotor;
import cads.impl.hal.server.HorizontalMotor;
import cads.impl.hal.server.VertikalMotor;
import cads.impl.mom.Middleware;
import cads.impl.mom.Watchdog;
import cads.impl.mom.buffer.Buffer;
import cads.impl.mom.buffer.Message;
import cads.impl.mom.middleware.ServerMiddleware;
import cads.impl.mom.watchdog.WatchdogServerSide;
import cads.impl.os.Server;
import cads.impl.os.UDPServer;

public class ServerApplication {

	private String clientHost = "localhost";
	private int gripperPort = 8012;
	private int horizontalPort = 8011;
	private int verticalPort = 8010;
	private int watchdogLocalPort = 9000;
	private int watchdogDestPort = 9001;
	private CaDSEV3RobotType robotType = CaDSEV3RobotType.SIMULATION;
	
	public void setRobotType(CaDSEV3RobotType robotType) {
		this.robotType = robotType;
	}

	public void setClientHost(String clientHost) {
		this.clientHost = clientHost;
	}

	public void setGripperPort(int gripperPort) {
		this.gripperPort = gripperPort;
	}

	public void setHorizontalPort(int horizontalPort) {
		this.horizontalPort = horizontalPort;
	}

	public void setVerticalPort(int verticalPort) {
		this.verticalPort = verticalPort;
	}

	public void setWatchdogLocalPort(String localPortString) {
		this.watchdogLocalPort = Integer.getInteger(localPortString);
	}

	public void setWatchdogDestPort(String destPortString) {
		this.watchdogDestPort = Integer.getInteger(destPortString);
	}

	public void nomain() throws JsonParseException, JsonMappingException, IOException {

		RobotStatusListener statusListener = new RobotStatusListener();
		CaDSEV3RobotHAL robot = CaDSEV3RobotHAL.createInstance(robotType, statusListener,
				new FeedBackListener());

		//horizontal
				Buffer<Message> horizontalBuffer = new Buffer<>();
		
		VertikalMotor vmotor = new VertikalMotor();
		statusListener.subscribe(ValueToObserve.VERTIKAL, vmotor);
		HorizontalMotor hmotor = new HorizontalMotor();
		statusListener.subscribe(ValueToObserve.HORIZONTAL, hmotor);
		GripperMotor gmotor = new GripperMotor();
		statusListener.subscribe(ValueToObserve.GRIPPER, gmotor);
		
		// vertikal
		Buffer<Message> vertikalBuffer = new Buffer<>();
		Server<String> vertikalServer = new UDPServer(verticalPort);
		Middleware vertikalMom = new ServerMiddleware(vertikalBuffer, vertikalServer);
		RobotController vertikalRobotController = new VertikalRobotController(vertikalBuffer, vmotor);
		
		
		Server<String> horizontalServer = new UDPServer(horizontalPort);
		Middleware horizontalMom = new ServerMiddleware(horizontalBuffer, horizontalServer);
		RobotController horizontalRobotController = new HorizontalRobotController(horizontalBuffer, hmotor);
		
		Buffer<Message> gripperBuffer = new Buffer<>();
		Server<String> gripperServer = new UDPServer(gripperPort);
		Middleware gripperMom = new ServerMiddleware(gripperBuffer, gripperServer);
		RobotController gripperRobotController = new GripperRobotController(gripperBuffer, gmotor);
		
		
		WatchdogServerSide w = new WatchdogServerSide(clientHost, watchdogDestPort, watchdogLocalPort, 500);
		w.registerObserver(vmotor);
		w.registerObserver(hmotor);
		w.registerObserver(gmotor);
		
		// start threads
		new Thread(vertikalMom).start();
		new Thread(vertikalRobotController).start();
		
		new Thread(horizontalMom).start();
		new Thread(horizontalRobotController).start();
		
		new Thread(gripperMom).start();
		new Thread(gripperRobotController).start();
		
		new Thread(w).start();
	}
	
//	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
//
//		RobotStatusListener statusListener = new RobotStatusListener();
//		CaDSEV3RobotHAL robot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, statusListener,
//				new FeedBackListener());
//
//		//horizontal
//				Buffer<Message> horizontalBuffer = new Buffer<>();
//		
//		VertikalMotor vmotor = new VertikalMotor();
//		statusListener.subscribe(ValueToObserve.VERTIKAL, vmotor);
//		HorizontalMotor hmotor = new HorizontalMotor();
//		statusListener.subscribe(ValueToObserve.HORIZONTAL, hmotor);
//		GripperMotor gmotor = new GripperMotor();
//		statusListener.subscribe(ValueToObserve.GRIPPER, gmotor);
//		
//		// vertikal
//		Buffer<Message> vertikalBuffer = new Buffer<>();
//		Server<String> vertikalServer = new UDPServer(8010);
//		Middleware vertikalMom = new ServerMiddleware(vertikalBuffer, vertikalServer);
//		RobotController vertikalRobotController = new VertikalRobotController(vertikalBuffer, vmotor);
//		
//		
//		Server<String> horizontalServer = new UDPServer(8011);
//		Middleware horizontalMom = new ServerMiddleware(horizontalBuffer, horizontalServer);
//		RobotController horizontalRobotController = new HorizontalRobotController(horizontalBuffer, hmotor);
//		
//		Buffer<Message> gripperBuffer = new Buffer<>();
//		Server<String> gripperServer = new UDPServer(8012);
//		Middleware gripperMom = new ServerMiddleware(gripperBuffer, gripperServer);
//		RobotController gripperRobotController = new GripperRobotController(gripperBuffer, gmotor);
//		
//		
//		WatchdogServerSide w = new WatchdogServerSide("localhost", 9000, 9001, 500);
//		w.registerObserver(vmotor);
//		w.registerObserver(hmotor);
//		w.registerObserver(gmotor);
//		
//		// start threads
//		new Thread(vertikalMom).start();
//		new Thread(vertikalRobotController).start();
//		
//		new Thread(horizontalMom).start();
//		new Thread(horizontalRobotController).start();
//		
//		new Thread(gripperMom).start();
//		new Thread(gripperRobotController).start();
//		
//		new Thread(w).start();
//	}
}
