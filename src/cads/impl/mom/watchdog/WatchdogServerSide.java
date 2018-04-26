package cads.impl.mom.watchdog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.fasterxml.jackson.core.JsonProcessingException;

import cads.impl.mom.MarshallingService;
import cads.impl.mom.Message;
import cads.impl.mom.Message.MsgType;

public class WatchdogServerSide extends Watchdog {

	private int seq = 0;
	private MarshallingService mas;

	public WatchdogServerSide(String dest_ip, int port) throws SocketException, UnknownHostException {
		super(dest_ip, port);
		this.mas = new MarshallingService();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		  while (!Thread.interrupted()) {
			  doServer();
		  }
	}

	public void doServer() {
		Message pingMsg = new Message(MsgType.PING, 0, 0, seq);
		String ping;
		try {
			ping = mas.serialize(pingMsg);
			Thread.sleep(1000);
			client.send(ping);
		} catch (JsonProcessingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// check ACK
		try {
			receiveData = server.receive();
		} catch (Exception e) {
			// TODO: handle exception - TIMEOUT
			System.out.println("Timeout!");
		}

		String msg = new String(receiveData);
		Message rmes;
		try {
			rmes = mas.deSerialize(msg, Message.class);
			if (rmes.getSeqId() == seq) {
				System.out.println("cool");
			} else {
				// TODO some handling
				System.out.println("not cool " + seq + ", " + msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		seq++;
	}

}
