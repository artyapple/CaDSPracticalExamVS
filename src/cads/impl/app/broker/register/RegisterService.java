package cads.impl.app.broker.register;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import cads.impl.app.broker.forward.ForwardingService;
import cads.impl.factory.Factory;
import cads.impl.mom.MarshallingService;
import cads.impl.rpc.data.Provider;

public class RegisterService implements Runnable {

	private final static int MAX_MESSAGE_SIZE = 4096;
	private MarshallingService marshalingService;
	private ForwardingService fwdService;
	private DatagramSocket socket;

	public RegisterService(String ip, int port) throws SocketException, InstantiationException, IllegalAccessException {

		Factory fac = Factory.current();
		this.fwdService = fac.getInstance(ForwardingService.class);
		this.fwdService.setIpAddr(ip);
		this.marshalingService = fac.getInstance(MarshallingService.class);
		InetSocketAddress address = new InetSocketAddress(ip, port);
		this.socket = new DatagramSocket(address);
	}

	@Override
	public void run() {
		System.out.println("Starte RegisterService");
		
		while (true) {
			try {
				byte[] receiveData = new byte[MAX_MESSAGE_SIZE];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(receivePacket); 
				if (receivePacket != null) {
					registerProvider(receivePacket, new String(receivePacket.getData()));
					//System.out.println(new String(receivePacket.getData()));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void registerProvider(DatagramPacket packet, String message) throws Exception {
		Provider provider = marshalingService.deSerialize(message, Provider.class);
		fwdService.registerProvider(packet, provider);
	}
}
