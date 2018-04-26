package cads.impl.os;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPServer implements Server<String> {

	private DatagramSocket serverSocket;

	public UDPServer(int port) throws SocketException {
		serverSocket = new DatagramSocket(port);
	}

	public UDPServer(int port, int timeout) throws SocketException {
		this(port);
		this.serverSocket.setSoTimeout(timeout);

	}

	// @Override
	// public byte[] receive() {
	// System.out.println("server start");
	// byte[] receiveData = new byte[BUFFER_SIZE];
	// DatagramPacket receivePacket = new DatagramPacket(receiveData,
	// receiveData.length);
	// try {
	// serverSocket.receive(receivePacket);
	// Logger.getLogger(UDPClient.class.getName()).log(Level.INFO,
	// "Receive message successfully:\n" + getInfo());
	// } catch (IOException e) {
	// Logger.getLogger(UDPServer.class.getName()).log(Level.WARNING,
	// "Receive message failed:\n" + getInfo() + "\n", e);
	// }
	// System.out.println("server end");
	// return receiveData;
	// }

	@Override
	public byte[] receive() throws IOException {
		byte[] receiveData = new byte[BUFFER_SIZE];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		serverSocket.receive(receivePacket);
		Logger.getLogger(UDPClient.class.getName()).log(Level.INFO, "Receive message successfully:\n" + getInfo());

		return receiveData;
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