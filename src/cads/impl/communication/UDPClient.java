package cads.impl.communication;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class UDPClient implements Client<String> {

	private DatagramSocket clientSocket;

	public UDPClient(InetAddress iPAddress, int port) throws SocketException {
		this.clientSocket = new DatagramSocket(port, iPAddress);
	}

	@Override
	public void send(String msg) {
		byte[] sendData = msg.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
		try {
			clientSocket.send(sendPacket);
			Logger.getLogger(UDPClient.class.getName()).log(Level.INFO, "Send message successfully:\n" + getInfo());
		} catch (IOException e) {
			Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING,
					"Send message failed:\n" + getInfo() + "\n" + msg, e);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

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
