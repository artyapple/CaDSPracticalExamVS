package cads.impl.mom.watchdog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import cads.impl.mom.Watchdog;

public class WatchdogClientSide extends Watchdog {

	public WatchdogClientSide(String dest_ip, int dest_port, int local_port) throws SocketException, UnknownHostException {
		super(dest_ip, dest_port, local_port);
	}

	@Override
	public void run() {
		  while (!Thread.interrupted()) {
			  pingServer();
		  }

	}
	
	public void pingServer() {
		try {
			receiveData = server.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.send(new String(receiveData));
	}

}
