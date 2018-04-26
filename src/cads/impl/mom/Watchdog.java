package cads.impl.mom;

import java.io.IOException;
import cads.impl.os.Client;
import cads.impl.os.Server;
import cads.impl.os.UDPClient;
import cads.impl.os.UDPServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import cads.impl.os.Client;
import cads.impl.os.Server;

public class Watchdog implements Runnable {

	public enum Type {
		SERVER, CLIENT
	}

//	private DatagramSocket socket;
//	private InetAddress dest_ip;
//	private int port;
	private Type type;
	private Client<String> client;
	private Server<String> server;

	public Watchdog(InetAddress dest_ip, int port, Type type) throws SocketException {
		
		
		this.type = type;
		if (type == Type.SERVER) {
			this.server = new UDPServer(port, true);
		} else { // Client
			this.client = new UDPClient(dest_ip, port);
		}
		
//		this.dest_ip = dest_ip;
//		this.port = port;
//		if (type == Type.SERVER) {
//			try {
//				socket.setSoTimeout(100); // 100ms until timeout for receive
//			} catch (SocketException e) {
//				e.printStackTrace();
//			}
//		}
	}

//	private void send(String msg) {
//		byte[] sendData = msg.getBytes();
//		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, dest_ip, port);
//		try {
//			socket.send(sendPacket);
//			System.out.println("send" + msg);
//		} catch (IOException e) {
//			
//		}
//	}
//
//	public byte[] receive() throws IOException {
//
//		byte[] receiveData = new byte[1024];
//		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//		socket.receive(receivePacket);
//		System.out.println("receive" + receiveData);
//		return receiveData;
//
//	}

	@Override
	public void run() {
		int seq = 0;
		byte[] receiveData = null;
		while (!Thread.interrupted()) {
			if (type == Type.SERVER) {
				try {Thread.sleep(1000);} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// send ping
				try {
					server.send(("" + seq), InetAddress.getByName("localhost"), 8099);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				
				// check ACK
				try {
					receiveData = server.receive();
				} catch (Exception e) {
					// TODO: handle exception - TIMEOUT
					System.out.println("Timeout!");
				}
				
				String msg = new String(receiveData);
				System.out.println(msg);
				if (msg.equals(seq)) {
					System.out.println("cool");
				} else {
					// TODO some handling
					System.out.println("not cool " + seq + ", " + msg);
				}
				seq++;
			} else {//if (type == Type.CLIENT) {
				receiveData = client.receive();
				client.send(receiveData.toString());
			}

		}

	}

}
