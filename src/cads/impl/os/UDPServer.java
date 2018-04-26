package cads.impl.os;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import cads.impl.mom.MessageHandler;

public class UDPServer implements Server<String> {

	private DatagramSocket serverSocket;

	public UDPServer(int port) throws SocketException {
		serverSocket = new DatagramSocket(port);
	}
	
	public UDPServer(int port, boolean value) throws SocketException {
		serverSocket = new DatagramSocket(port);
		serverSocket.setSoTimeout(500);
	}

	@Override
	public byte[] receive() {
		while (true) {
			byte[] receiveData = new byte[BUFFER_SIZE];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
				Logger.getLogger(UDPClient.class.getName()).log(Level.INFO,
						"Receive message successfully:\n" + getInfo());
			} catch (IOException e) {
				Logger.getLogger(UDPServer.class.getName()).log(Level.WARNING,
						"Receive message failed:\n" + getInfo() + "\n", e);
			}
			return receiveData; 
		}
	}

	public void send(String msg, InetAddress dest_ip, int port) {
		byte[] sendData = msg.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, dest_ip, port);
		try {
			serverSocket.send(sendPacket);
			Logger.getLogger(UDPClient.class.getName()).log(Level.INFO, "Send message successfully:\n" + getInfo());
		} catch (IOException e) {
			Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING,
					"Send message failed:\n" + getInfo() + "\n" + msg, e);
		}
	}
	
	@Override
	public void stop() {
		serverSocket.close();
	}

	private String getInfo() {
		StringBuilder info = new StringBuilder("Client - IP:");
		return info.append(serverSocket.getInetAddress() + ":" + serverSocket.getPort()).toString();
	}
}