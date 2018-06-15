package cads.impl.app.broker.forward;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;

import cads.impl.app.broker.data.BrokerProvider;
import cads.impl.app.broker.data.BrokerService;
import cads.impl.app.broker.data.RegisteredProviders;
import cads.impl.factory.Factory;
import cads.impl.rpc.data.Provider;
import cads.impl.rpc.data.Service;

public class ForwardingService {

	private static final int START_PORT = 10005;
	private String brokerip;
	private int count;
	private int lastGeneratedPort;
	private RegisteredProviders registeredProviders;
	private HashMap<String, ForwardingPipe> pipes;

	public ForwardingService() throws InstantiationException, IllegalAccessException {
		this.lastGeneratedPort = START_PORT;
		this.pipes = new HashMap<String, ForwardingPipe>();
		this.registeredProviders = Factory.current().<RegisteredProviders>getInstance(RegisteredProviders.class);
	}
	
	

	public void registerProvider(DatagramPacket packet, Provider provider)
			throws InstantiationException, IllegalAccessException, UnknownHostException, SocketException {
		String key = packet.getSocketAddress().toString();
		InetAddress ipAddress = packet.getAddress();
		BrokerProvider bProvider = new BrokerProvider(provider);
		bProvider.setIpAddress(ipAddress);
		boolean result = registeredProviders.addProvider(key, bProvider);

		if (result) {
			bProvider.getProvider().setAlias("iRobot" + (++count));
			System.out.println("Registered: " + key);
			for (Service service : provider.getServices()) {
				registerService(ipAddress, bProvider, service);
			}
		} else {
			System.out.println("Register failed, find a key: " + key);
		}
	}

	public void registerService(InetAddress ipAddress, BrokerProvider provider, Service service)
			throws InstantiationException, IllegalAccessException, UnknownHostException, SocketException {

		String key = ipAddress.toString() + service.getPort();
		if (!pipes.containsKey(key)) {

			System.out.println("Register Service: " + key);
			BrokerService bService = new BrokerService(service);
			bService.setSendingPort(service.getPort());

			int receivingPort = getNewPort();
			// set port for broker
			bService.setReceivingPort(receivingPort);
			// set port for consumer
			bService.setPortForConsumer(receivingPort);

			ForwardingPipe pipe = new ForwardingPipe(brokerip, provider, bService, ipAddress);
			this.pipes.put(key, pipe);
			pipe.start();
		} else {
			System.out.println("Register Service failed, find a key: " + key);
		}
	}

	private int getNewPort() {
		return (lastGeneratedPort ++);
	}
	
	public void setIpAddr(String ip){
		brokerip = ip;
	}
}
