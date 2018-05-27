package cads.impl.app.client;

import java.net.SocketException;
import java.net.UnknownHostException;

import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;

import cads.impl.app.client.gui.GripperMoveGuiController;
import cads.impl.app.client.gui.HorizontalMoveGuiController;
import cads.impl.app.client.gui.VerticalMoveGuiController;
import cads.impl.mom.IBuffer;
import cads.impl.mom.Middleware;
import cads.impl.mom.buffer.Buffer;
import cads.impl.mom.buffer.Message;
import cads.impl.mom.middleware.ClientMiddleware;
import cads.impl.mom.watchdog.WatchdogClientSide;
import cads.impl.os.Client;
import cads.impl.os.UDPClient;

public class ClientApplication {

	private String serverHost = "localhost";
	private int gripperPort = 8012;
	private int horizontalPort = 8011;
	private int verticalPort = 8010;
	private int watchdogLocalPort = 9001;
	private int watchdogDestPort = 9000;

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
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
	
	public void nomain()
			throws InstantiationException, IllegalAccessException, SocketException, UnknownHostException {

		IBuffer<Message> vertikalBuffer = new Buffer<>();
		Client<String> vertikalUpd = new UDPClient(serverHost, verticalPort);		
		IIDLCaDSEV3RMIMoveVertical vertikal = new VerticalMoveGuiController(vertikalBuffer);
		Middleware vertikalMom = new ClientMiddleware(vertikalBuffer, vertikalUpd);

		IBuffer<Message> horizontalBuffer = new Buffer<>();
		Client<String> horizontalUdp = new UDPClient(serverHost, horizontalPort);
		IIDLCaDSEV3RMIMoveHorizontal horizontal = new HorizontalMoveGuiController(horizontalBuffer);
		Middleware horizontalMom = new ClientMiddleware(horizontalBuffer, horizontalUdp);

		IBuffer<Message> gripperBuffer = new Buffer<>();
		Client<String> gripperUdp = new UDPClient(serverHost, gripperPort);
		IIDLCaDSEV3RMIMoveGripper gripper = new GripperMoveGuiController(gripperBuffer);
		Middleware gripperMom = new ClientMiddleware(gripperBuffer, gripperUdp);
		
		CaDSRobotGUISwing gui = new CaDSRobotGUISwing(null, gripper, vertikal, horizontal, null);
		
		new Thread(vertikalMom).start();
		new Thread(horizontalMom).start();
		new Thread(gripperMom).start();
		
		// Watchdog
		WatchdogClientSide w = new WatchdogClientSide(serverHost, watchdogDestPort, watchdogLocalPort);
		
		new Thread(w).start();;

	}
	
//	public static void main(String[] args)
//	throws InstantiationException, IllegalAccessException, SocketException, UnknownHostException {
//
//IBuffer<Message> vertikalBuffer = new Buffer<>();
//Client<String> vertikalUpd = new UDPClient("localhost", 8010);		
//IIDLCaDSEV3RMIMoveVertical vertikal = new VerticalMoveGuiController(vertikalBuffer);
//Middleware vertikalMom = new ClientMiddleware(vertikalBuffer, vertikalUpd);
//
//IBuffer<Message> horizontalBuffer = new Buffer<>();
//Client<String> horizontalUdp = new UDPClient("localhost", 8011);
//IIDLCaDSEV3RMIMoveHorizontal horizontal = new HorizontalMoveGuiController(horizontalBuffer);
//Middleware horizontalMom = new ClientMiddleware(horizontalBuffer, horizontalUdp);
//
//IBuffer<Message> gripperBuffer = new Buffer<>();
//Client<String> gripperUdp = new UDPClient("localhost", 8012);
//IIDLCaDSEV3RMIMoveGripper gripper = new GripperMoveGuiController(gripperBuffer);
//Middleware gripperMom = new ClientMiddleware(gripperBuffer, gripperUdp);
//
//CaDSRobotGUISwing gui = new CaDSRobotGUISwing(null, gripper, vertikal, horizontal, null);
//
//new Thread(vertikalMom).start();
//new Thread(horizontalMom).start();
//new Thread(gripperMom).start();
//
//// Watchdog
//WatchdogClientSide w = new WatchdogClientSide("localhost", 9001, 9000);
//
//new Thread(w).start();;
//
//}
	
}
