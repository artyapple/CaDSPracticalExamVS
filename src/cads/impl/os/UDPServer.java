package cads.impl.os;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPServer implements Server<String> {

	private DatagramSocket serverSocket;

	/**
	 * @param local_addr local address to bind
	 * @param local_port local port to use
	 * @param timeout the specified timeout in milliseconds.
	 * 
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public UDPServer(String local_addr, int local_port, int timeout) throws SocketException, UnknownHostException {
		this.serverSocket = new DatagramSocket(local_port, InetAddress.getByName(local_addr));
		this.serverSocket.setSoTimeout(timeout);
	}
	/**
	 * @param local_addr local address to bind
	 * @param local_port local port to use
	 * 
	 * timeout disabled
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
	public UDPServer(String local_addr, int local_port) throws SocketException, UnknownHostException {
		this(local_addr, local_port, 0);
	}
	/**
	 * @param local_port local port to use
	 * 
	 * local host address to bind
	 * timeout disabled
	 * @throws SocketException
	 */
	public UDPServer(int local_port) throws SocketException {
		this.serverSocket = new DatagramSocket(local_port);
	}
	/**
	 * 
	 * @param local_port
	 * @param timeout
	 * 
	 * local host address to bind
	 * @throws SocketException
	 */
	public UDPServer(int local_port, int timeout) throws SocketException {
		this(local_port);
		this.serverSocket.setSoTimeout(timeout);
	}

	/**
	 * 
	 * @param socket
	 * @throws SocketException
	 */
	public UDPServer(DatagramSocket socket) throws SocketException {
		this.serverSocket = socket;
	}
	
//	@Override
//	public byte[] receive() throws IOException {
//		System.out.println("server start");
//		byte[] receiveData = new byte[BUFFER_SIZE];
//		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//		try {
//			if(serverSocket.getSoTimeout()==0){
//				try {
//					serverSocket.receive(receivePacket);
//					Logger.getLogger(UDPClient.class.getName()).log(Level.INFO, "Receive message successfully:\n" + getInfo());
//				} catch (IOException e) {
//					Logger.getLogger(UDPServer.class.getName()).log(Level.WARNING,
//							"Receive message failed:\n" + getInfo() + "\n", e);
//				}
//			} else {
//				serverSocket.receive(receivePacket);
//			}
//		} catch (SocketException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("server end");
//		return receiveData;
//	}

	@Override
	public byte[] receive() throws IOException {
		byte[] receiveData = new byte[BUFFER_SIZE];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		serverSocket.receive(receivePacket);
		
		return receiveData;
	}

	@Override
	public void stop() {
		serverSocket.close();
	}

}