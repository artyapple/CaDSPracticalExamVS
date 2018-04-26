package cads.impl.os;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import cads.impl.mom.MessageHandler;

public class UDPClient implements Client<String> {

	private DatagramSocket clientSocket;
	private int port;
	private InetAddress dest_ip;

	public UDPClient(InetAddress iPAddress, int port) throws SocketException {
		this.clientSocket = new DatagramSocket();
		this.port = port;
		this.dest_ip = iPAddress;
	}

	public byte[] receive() {
		while (true) {
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				clientSocket.receive(receivePacket);
				Logger.getLogger(UDPClient.class.getName()).log(Level.INFO,
						"Receive message successfully:\n" + getInfo());
			} catch (IOException e) {
				Logger.getLogger(UDPServer.class.getName()).log(Level.WARNING,
						"Receive message failed:\n" + getInfo() + "\n", e);
			}
			return receiveData; 
		}
	}
	
	@Override
	public void send(String msg) {
		byte[] sendData = msg.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, dest_ip, port);
		try {
			clientSocket.send(sendPacket);
			Logger.getLogger(UDPClient.class.getName()).log(Level.INFO, "Send message successfully:\n" + getInfo());
		} catch (IOException e) {
			Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING,
					"Send message failed:\n" + getInfo() + "\n" + msg, e);
		}
	}

	public void send(String msg, InetAddress dest_ip, int port) {
		byte[] sendData = msg.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, dest_ip, port);
		try {
			clientSocket.send(sendPacket);
			Logger.getLogger(UDPClient.class.getName()).log(Level.INFO, "Send message successfully:\n" + getInfo());
		} catch (IOException e) {
			Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING,
					"Send message failed:\n" + getInfo() + "\n" + msg, e);
		}
	}
	@Override
	public void stop() {
		clientSocket.close();
	}

	private String getInfo() {
		StringBuilder info = new StringBuilder("Client - IP:");
		return info.append(clientSocket.getInetAddress() + ":" + clientSocket.getPort()).toString();
	}
}
