package cads.impl.mom.watchdog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import cads.impl.app.server.listener.ObservableValue;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.Message;
import cads.impl.mom.Message.MsgType;
import cads.impl.os.UDPClient;

public class WatchdogServerSide extends Watchdog {

	private MarshallingService mas;
	private ObservableValue<Boolean> connectionOk = new ObservableValue<Boolean>(Boolean.TRUE);
	private AtomicInteger seq = new AtomicInteger();

	public WatchdogServerSide(String dest_ip, int dest_port, int local_port, int timeout)
			throws SocketException, UnknownHostException {
		super(dest_ip, dest_port, local_port, timeout);
		this.mas = new MarshallingService();
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			pingClient();
		}
	}

	public void pingClient() {
		Message pingMsg = new Message(MsgType.PING, 0, 0, seq.incrementAndGet());
		String ping;
		try {
			ping = mas.serialize(pingMsg);
			Thread.sleep(100);
			client.send(ping);
		} catch (JsonProcessingException | InterruptedException e) {
			e.printStackTrace();
		}

		try {
			receiveData = server.receive();
		} catch (Exception e) {
			connectionOk.setValue(false);
			Logger.getLogger(UDPClient.class.getName()).log(Level.SEVERE,
					"Ping failed. Stop all motors.");
		}

		String msg = new String(receiveData);
		Message rmes;
		try {
			rmes = mas.deSerialize(msg, Message.class);
			if (rmes.getSeqId() != seq.get()) {
				connectionOk.setValue(false);
				Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING,
						"Ping message lost. Stop all motors.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
