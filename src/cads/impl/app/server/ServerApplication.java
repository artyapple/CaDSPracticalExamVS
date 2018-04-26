package cads.impl.app.server;

import java.io.IOException;
import java.net.InetAddress;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;
import org.cads.ev3.middleware.hal.CaDSEV3Robot;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotFeedBackListener;
import org.cads.ev3.middleware.hal.ICaDSEV3RobotStatusListener;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cads.impl.app.client.gui.VerticalMovingGuiController;
import cads.impl.app.server.controller.HorizontalRobotController;
import cads.impl.app.server.controller.RobotController;
import cads.impl.app.server.controller.VertikalRobotController;
import cads.impl.app.server.listener.FeedBackListener;
import cads.impl.app.server.listener.RobotStatusListener;
import cads.impl.app.server.listener.RobotStatusListener.ValueToObserve;
import cads.impl.app.server.listener.Status;
import cads.impl.hal.IVertikalMotor;
import cads.impl.hal.server.GripperMotor;
import cads.impl.hal.server.HorizontalMotor;
import cads.impl.hal.server.VertikalMotor;
import cads.impl.mom.ClientMiddleware;
import cads.impl.mom.IMessage;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.Message;
import cads.impl.mom.MessageBuffer;
import cads.impl.mom.Middleware;
import cads.impl.mom.ServerMiddleware;
import cads.impl.mom.watchdog.Watchdog;
import cads.impl.mom.watchdog.WatchdogServerSide;
import cads.impl.mom.Message.MsgType;
import cads.impl.os.Client;
import cads.impl.os.Server;
import cads.impl.os.UDPClient;
import cads.impl.os.UDPServer;

public class ServerApplication {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		RobotStatusListener statusListener = new RobotStatusListener();
		CaDSEV3RobotHAL robot = CaDSEV3RobotHAL.createInstance(CaDSEV3RobotType.SIMULATION, statusListener,
				new FeedBackListener());

		VertikalMotor vmotor = new VertikalMotor();
		statusListener.subscribe(ValueToObserve.VERTIKAL, vmotor);
		HorizontalMotor hmotor = new HorizontalMotor();
		statusListener.subscribe(ValueToObserve.HORIZONTAL, hmotor);
		GripperMotor gmotor = new GripperMotor();
		statusListener.subscribe(ValueToObserve.GRIPPER, gmotor);
		
		// vertikal
		MessageBuffer<Message> vertikalBuffer = new MessageBuffer<>();
		Server<String> vertikalServer = new UDPServer(8010);
		Middleware vertikalMom = new ServerMiddleware(vertikalBuffer, vertikalServer);
		RobotController vertikalRobotController = new VertikalRobotController(vertikalBuffer, vmotor);
		
		//horizontal
		MessageBuffer<Message> horizontalBuffer = new MessageBuffer<>();
		Server<String> horizontalServer = new UDPServer(8011);
		Middleware horizontalMom = new ServerMiddleware(horizontalBuffer, horizontalServer);
		RobotController horizontalRobotController = new HorizontalRobotController(horizontalBuffer, hmotor);
		
		// start threads
		new Thread(vertikalMom).start();
		new Thread(vertikalRobotController).start();
		
		new Thread(horizontalMom).start();
		new Thread(horizontalRobotController).start();
		
		//Watchdog w = new WatchdogServerSide("localhost", 9001);
	}
}
