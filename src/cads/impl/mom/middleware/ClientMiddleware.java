package cads.impl.mom.middleware;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import cads.impl.mom.IBuffer;
import cads.impl.mom.Middleware;
import cads.impl.os.Client;
import cads.impl.os.UDPClient;

public class ClientMiddleware extends Middleware {

	private Client<String> client;

	public ClientMiddleware(IBuffer<String> buffer, Client<String> client)
			throws SocketException, UnknownHostException {
		super(buffer);
		this.client = client;
		new Thread(this).start();
	}
	
	public ClientMiddleware(int targetPort, String targetIp, IBuffer<String> buffer)
			throws SocketException, UnknownHostException {
		super(buffer);
		this.client = new UDPClient(targetIp, targetPort);
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(true){
			sendNextMessage();
		}
	}

	public synchronized void sendNextMessage() {
		
		String message = buffer.getLast();
		client.send(message);
	}
	
	public DatagramSocket getSocket(){
		return client.getSocket();
	}

}
