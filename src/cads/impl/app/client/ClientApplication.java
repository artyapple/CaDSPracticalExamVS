package cads.impl.app.client;

import java.awt.FlowLayout;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;

import cads.impl.app.client.gui.VerticalMovingGuiController;
import cads.impl.mom.Message;
import cads.impl.mom.MessageBuffer;
import cads.impl.mom.MessageHandler;
import cads.impl.os.Client;
import cads.impl.os.Server;
import cads.impl.os.UDPClient;
import cads.impl.os.UDPServer;

public class ClientApplication {
	
	//static CaDSRobotGUISwing gui;

	
	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, SocketException, UnknownHostException {
		
		//VERTIKAL SERVICE and VERTIKAL MESSAGE HANDLER SETUP
		//1)create vertikal buffer
		MessageBuffer<Message> buffer = new MessageBuffer<>();
		//2)create vertikal gui controller 
		IIDLCaDSEV3RMIMoveVertical vertikal = new VerticalMovingGuiController(buffer);
		//3)NETWORK SETUP for vertikal!
		// servers ip and port ?! klaren
		Client<String> client= new UDPClient(InetAddress.getByName("localhost"), 8010);
		//  port ?
		Server<String> server = new UDPServer(8010);		
		// NETWORK SETUP END for vertikal
		//4) Message handler for vertikal
		MessageHandler vertikalMom = new MessageHandler(buffer, client, server);
		//5)vertikal ready
		
		//HERE other handlers and other services
		
		CaDSRobotGUISwing gui = new CaDSRobotGUISwing(null, null, vertikal , null, null);
		//gui.start oder wie auch immer
	
	}

}
