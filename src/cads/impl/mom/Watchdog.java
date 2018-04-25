package cads.impl.mom;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import cads.impl.os.UDPClient;
import cads.impl.os.UDPServer;

public class Watchdog implements Runnable {

	public enum Type {
		SERVER, CLIENT
	}

	private DatagramSocket socket;
	private InetAddress dest_ip;
	private int port;
	private Type type;

	public Watchdog(InetAddress dest_ip, int port, Type type) {
		this.dest_ip = dest_ip;
		this.port = port;
		this.type = type;
		if (type == Type.SERVER) {
			try {
				socket.setSoTimeout(100); // 100ms until timeout for receive
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
	}

	private void send(String msg) {
		byte[] sendData = msg.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, dest_ip, port);
		try {
			socket.send(sendPacket);
			System.out.println("send" + msg);
		} catch (IOException e) {
			
		}
	}

	public byte[] receiveACK() {

		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			socket.receive(receivePacket);
			System.out.println("receive" + receiveData);
		} catch (IOException e) {
			
		}
		return receiveData;

	}

	@Override
	public void run() {
		int seq = 0;
		byte[] receiveData = null;
		while (!Thread.interrupted()) {
			if (type == Type.SERVER) {
				// send ping
				send("" + seq);
				
				// check ACK
				try {
					receiveData = receiveACK();
				} catch (Exception e) {
					// TODO: handle exception - TIMEOUT
					System.out.println("Timeout!");
				}
				
				String msg = new String(receiveData);
				if (msg.equals(seq)) {
					System.out.println("cool");
				} else {
					// TODO some handling
					System.out.println("not cool " + seq + ", " + msg);
				}
				
				try {Thread.sleep(1000);} catch (InterruptedException e) {
					e.printStackTrace();
				}
				seq++;
			} else if (type == Type.CLIENT) {
				// return ping as ACK
				receiveData = receiveACK();
				send(receiveData.toString());
			}

		}

	}

}
