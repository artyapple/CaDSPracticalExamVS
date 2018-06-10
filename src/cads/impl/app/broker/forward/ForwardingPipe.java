package cads.impl.app.broker.forward;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import cads.impl.app.broker.data.BrokerProvider;
import cads.impl.app.broker.data.BrokerService;
import cads.impl.app.broker.data.ReceivedPacket;
import cads.impl.mom.buffer.Buffer;

public class ForwardingPipe {
	
	private final int BUFFER_MAX_SIZE = 10;
	
	private ForwardingSender sender;
	private ForwardingReceiver receiver;
	
	private Buffer<ReceivedPacket> senderBuffer;
	private Buffer<ReceivedPacket> receiverBuffer;
	
	private BrokerProvider provider;
	private BrokerService service;
	
	public ForwardingPipe(BrokerProvider provider, BrokerService service, InetAddress ipAddress) 
			throws InstantiationException, IllegalAccessException, UnknownHostException, SocketException{
		this.provider = provider;
		this.service = service;
		this.senderBuffer = new Buffer<>(BUFFER_MAX_SIZE);
		this.receiverBuffer = new Buffer<>(BUFFER_MAX_SIZE);
		this.sender = new ForwardingSender(ipAddress, service.getSendingPort(), receiverBuffer, senderBuffer);
		this.receiver = new ForwardingReceiver(service.getReceivingPort(), receiverBuffer, senderBuffer);
	}
	
	public void start() {
		new Thread(this.sender).start();
		new Thread(this.receiver).start();
	}

	public BrokerService getService() {
		return service;
	}

	public void setService(BrokerService service) {
		this.service = service;
	}

	public BrokerProvider getProvider() {
		return provider;
	}

	public void setProvider(BrokerProvider provider) {
		this.provider = provider;
	}
}
