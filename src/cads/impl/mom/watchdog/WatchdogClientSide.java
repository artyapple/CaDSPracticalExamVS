package cads.impl.mom.watchdog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class WatchdogClientSide extends Watchdog {

	public WatchdogClientSide(String dest_ip, int port) throws SocketException, UnknownHostException {
		super(dest_ip, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		  while (!Thread.interrupted()) {
			  doClient();
		  }

	}
	
	public void doClient() {
		try {
			receiveData = server.receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.send(new String(receiveData));
	}

}
