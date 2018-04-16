package cads.impl.app;

import java.net.InetAddress;

import cads.impl.communication.Client;
import cads.impl.communication.UDPClient;
import cads.impl.mom.IBuffer;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.Message;
import cads.impl.mom.MessageHandler;

public class GripperService {

	private int port = 8080;
	private String dest_ip = "0.0.0.0";
	private IBuffer<Message> buffer;
	MessageHandler handler = new MessageHandler(buffer, new MarshallingService());
	Client<String> udp;
	
	public GripperService() {
		//Client<String> udp = new UDPClient(InetAddress.getByName("localhost"), port, handler);
	}
	
	public void send(IBuffer<Message> buffer) {
		
	}
}
