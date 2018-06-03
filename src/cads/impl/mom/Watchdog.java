package cads.impl.mom;

import java.net.SocketException;
import java.net.UnknownHostException;

import cads.impl.os.Client;
import cads.impl.os.Server;
import cads.impl.os.UDPClient;
import cads.impl.os.UDPServer;

public abstract class Watchdog implements Runnable {

	protected Client<String> client;
	protected Server<String> server;
	protected byte[] receiveData = null;

	public Watchdog(String dest_ip, int dest_port, int local_port, int timeout) throws SocketException, UnknownHostException {
		this.server = new UDPServer(local_port, timeout);
		this.client = new UDPClient(dest_ip, dest_port);
	}
	
	public Watchdog(String dest_ip, int dest_port, int local_port) throws SocketException, UnknownHostException {
		this(dest_ip, dest_port, local_port, 0);
	}

	@Override
	public abstract void run();
}
