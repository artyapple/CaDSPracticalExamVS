package cads.impl.app.client.service;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import cads.impl.hal.IVertikalMotor;
import cads.impl.hal.client.VertikalMotor;
import cads.impl.mom.IMessage;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.Message;
import cads.impl.mom.MessageBuffer;
import cads.impl.os.UDPClient;

public class ServiceVertikalMotor {
	
	private MessageBuffer<Message> buffer;
	private IVertikalMotor vertikalMotor;
	
	public ServiceVertikalMotor(MessageBuffer<Message> buffer) throws SocketException, UnknownHostException{
		//factory
		this.buffer = buffer;
		//
		this.vertikalMotor = new VertikalMotor(buffer);	
	}	

	public void move(int percent) {
		vertikalMotor.move(percent);
	}

}
