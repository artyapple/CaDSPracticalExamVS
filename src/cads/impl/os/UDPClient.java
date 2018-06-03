package cads.impl.os;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UDPClient implements Client<String> {

	private DatagramSocket clientSocket;
	private int dest_port;
	private InetAddress dest_addr;
	
	/**
	 * @param local_addr  local address to bind
	 * @param local_port local port to use
	 * @param dest_addr destination
	 * @param dest_port destination
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public UDPClient(String local_addr, int local_port,String dest_addr, int dest_port) throws SocketException, UnknownHostException {
		this.clientSocket = new DatagramSocket(local_port, InetAddress.getByName(local_addr));
		this.dest_port = dest_port;
		this.dest_addr = InetAddress.getByName(dest_addr);
	}
	
	public UDPClient(String dest_addr, int dest_port) throws SocketException, UnknownHostException {
		this.clientSocket = new DatagramSocket();
		this.dest_port = dest_port;
		this.dest_addr = InetAddress.getByName(dest_addr);
	}
	
	@Override
	public void send(String msg) {
		byte[] sendData = msg.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, dest_addr, dest_port);
		try {
			clientSocket.send(sendPacket);
			Logger.getLogger(UDPClient.class.getName()).log(Level.INFO, "Send message successfully:\n" + msg);
		} catch (IOException e) {
			Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING,
					"Send message failed:\n" + msg, e);
		}
	}
	
	@Override
	public void stop() {
		clientSocket.close();
	}

	@Override
	public DatagramSocket getSocket() {
		return clientSocket;
	}
}
