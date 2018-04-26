package cads.impl.mom.watchdog;

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

public abstract class Watchdog implements Runnable {

	protected Client<String> client;
	protected Server<String> server;
	protected byte[] receiveData = null;

	public Watchdog(String dest_ip, int port) throws SocketException, UnknownHostException {

		// timeout???
		this.server = new UDPServer(port);
		this.client = new UDPClient(dest_ip, port);
	}

	@Override
	public abstract void run();
}
