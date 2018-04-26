package cads.impl.app.client;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;

import cads.impl.app.client.gui.HorizontalMovingGuiController;
import cads.impl.app.client.gui.VerticalMovingGuiController;
import cads.impl.mom.ClientMiddleware;
import cads.impl.mom.Message;
import cads.impl.mom.MessageBuffer;
import cads.impl.mom.Middleware;
import cads.impl.os.Client;
import cads.impl.os.Server;
import cads.impl.os.UDPClient;
import cads.impl.os.UDPServer;

public class ClientApplication {

	// static CaDSRobotGUISwing gui;

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, SocketException, UnknownHostException {

		// VERTIKAL SERVICE and VERTIKAL MESSAGE HANDLER SETUP
		// 1)create vertikal buffer
		MessageBuffer<Message> buffer = new MessageBuffer<>();

		// 2)create vertikal gui controller
		IIDLCaDSEV3RMIMoveVertical vertikal = new VerticalMovingGuiController(buffer);

		// 3)NETWORK SETUP for vertikal!
		// servers ip and port ?! klaeren
		Client<String> client = new UDPClient(InetAddress.getByName("localhost"), 8010);
		// port ?
		// NETWORK SETUP END for vertikal

		// 4) Message handler for vertikal
		Middleware vertikalMom = new ClientMiddleware(buffer, client);
		// 5)vertikal ready

		// HERE other handlers and other services

		// HORIZONTAL SERVICE and HORIZONTAL MESSAGE HANDLER SETUP
		// horizontal buffer
		MessageBuffer<Message> horizontalMessageBuffer = new MessageBuffer<>();
		// horizontal gui controller
		IIDLCaDSEV3RMIMoveHorizontal horizontal = new HorizontalMovingGuiController(horizontalMessageBuffer);

		// 3)NETWORK SETUP for horizontal!
		// servers ip and port ?! klaeren
		Client<String> clientH = new UDPClient(InetAddress.getByName("localhost"), 8011);
		// port ?
		// Server<String> serverH = new UDPServer(8011);
		// NETWORK SETUP END for horizontal

		// 4) Message handler for horizontal
		Middleware horizontalMom = new ClientMiddleware(horizontalMessageBuffer, clientH);
		// 5)horizontal ready

		CaDSRobotGUISwing gui = new CaDSRobotGUISwing(null, null, vertikal, horizontal, null);
		// gui.start oder wie auch immer
		
		new Thread(vertikalMom).start();
		new Thread(horizontalMom).start();
		
		// Watchdog
		

	}

}
