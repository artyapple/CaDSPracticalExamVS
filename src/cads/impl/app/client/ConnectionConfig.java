package cads.impl.app.client;

import java.net.SocketException;
import java.net.UnknownHostException;

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
import cads.impl.os.Client;
import cads.impl.os.UDPClient;

public class ConnectionConfig {

	private String serverHost = "localhost";
	private int gripperPort = 8012;
	private int horizontalPort = 8011;
	private int verticalPort = 8010;
	private int watchdogLocalPort = 9001;
	private int watchdogDestPort = 9000;
	
	IIDLCaDSEV3RMIMoveVertical vertikal;
	IIDLCaDSEV3RMIMoveHorizontal horizontal;
	IIDLCaDSEV3RMIMoveGripper gripper;
	
	private Middleware vertikalMom;
	private Middleware horizontalMom;
	private Middleware gripperMom;
	
	public IIDLCaDSEV3RMIMoveVertical getVertikal() {
		return vertikal;
	}

	public IIDLCaDSEV3RMIMoveHorizontal getHorizontal() {
		return horizontal;
	}

	public IIDLCaDSEV3RMIMoveGripper getGripper() {
		return gripper;
	}

	public ConnectionConfig(String serverHost) {
		this.serverHost = serverHost;
	}

	public void start() throws SocketException, UnknownHostException {
		IBuffer<Message> vertikalBuffer = new Buffer<>();
		Client<String> vertikalUpd = new UDPClient(serverHost, verticalPort);		
		vertikal = new VerticalMoveGuiController(vertikalBuffer);
		Middleware vertikalMom = new ClientMiddleware(vertikalBuffer, vertikalUpd);
		
		IBuffer<Message> horizontalBuffer = new Buffer<>();
		Client<String> horizontalUdp = new UDPClient(serverHost, horizontalPort);
		horizontal = new HorizontalMoveGuiController(horizontalBuffer);
		Middleware horizontalMom = new ClientMiddleware(horizontalBuffer, horizontalUdp);

		IBuffer<Message> gripperBuffer = new Buffer<>();
		Client<String> gripperUdp = new UDPClient(serverHost, gripperPort);
		gripper = new GripperMoveGuiController(gripperBuffer);
		Middleware gripperMom = new ClientMiddleware(gripperBuffer, gripperUdp);
		
		new Thread(vertikalMom).start();
		new Thread(horizontalMom).start();
		new Thread(gripperMom).start();
	}
	
	public int getWatchdogLocalPort() {
		return watchdogLocalPort;
	}

	public void setWatchdogLocalPort(int watchdogLocalPort) {
		this.watchdogLocalPort = watchdogLocalPort;
	}

	public int getWatchdogDestPort() {
		return watchdogDestPort;
	}

	public void setWatchdogDestPort(int watchdogDestPort) {
		this.watchdogDestPort = watchdogDestPort;
	}

	public String getServerHost() {
		return serverHost;
	}

	public int getGripperPort() {
		return gripperPort;
	}

	public int getHorizontalPort() {
		return horizontalPort;
	}

	public int getVerticalPort() {
		return verticalPort;
	}

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
	
}
