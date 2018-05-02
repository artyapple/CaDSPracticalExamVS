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
import cads.impl.mom.ClientMiddleware;
import cads.impl.mom.IBuffer;
import cads.impl.mom.Message;
import cads.impl.mom.Buffer;
import cads.impl.mom.Middleware;
import cads.impl.os.Client;
import cads.impl.os.UDPClient;

public class ClientApplication {

	// static CaDSRobotGUISwing gui;

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, SocketException, UnknownHostException {

		IBuffer<Message> vertikalBuffer = new Buffer<>();
		Client<String> vertikalUpd = new UDPClient("localhost", 8010);		
		IIDLCaDSEV3RMIMoveVertical vertikal = new VerticalMoveGuiController(vertikalBuffer);
		Middleware vertikalMom = new ClientMiddleware(vertikalBuffer, vertikalUpd);

		IBuffer<Message> horizontalBuffer = new Buffer<>();
		Client<String> horizontalUdp = new UDPClient("localhost", 8011);
		IIDLCaDSEV3RMIMoveHorizontal horizontal = new HorizontalMoveGuiController(horizontalBuffer);
		Middleware horizontalMom = new ClientMiddleware(horizontalBuffer, horizontalUdp);

		IBuffer<Message> gripperBuffer = new Buffer<>();
		Client<String> gripperUdp = new UDPClient("localhost", 8012);
		IIDLCaDSEV3RMIMoveGripper gripper = new GripperMoveGuiController(gripperBuffer);
		Middleware gripperMom = new ClientMiddleware(gripperBuffer, gripperUdp);
		
		CaDSRobotGUISwing gui = new CaDSRobotGUISwing(null, gripper, vertikal, horizontal, null);
		// gui.start oder wie auch immer
		
		new Thread(vertikalMom).start();
		new Thread(horizontalMom).start();
		new Thread(gripperMom).start();
		
		// Watchdog
		

	}

}
