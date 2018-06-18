package cads.impl.app.broker.forward;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import cads.impl.app.broker.data.ReceivedPacket;
import cads.impl.factory.Factory;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.buffer.BrokerMessage;
import cads.impl.mom.buffer.Buffer;

public class ForwardingSender implements Runnable {

	private final int MAX_MESSAGE_SIZE = 1024;

	private DatagramSocket socket;
	private Buffer<ReceivedPacket> senderBuffer;
	private Buffer<ReceivedPacket> receiverBuffer;
	private MarshallingService marshallingService;

	private ForwardingSenderReceiver receiver;

	private int destinationPort;
	private InetAddress destinationAddress;

	public ForwardingSender(InetAddress ipAddress, int providerPort, Buffer<ReceivedPacket> receiverBuffer,
			Buffer<ReceivedPacket> senderBuffer)
			throws InstantiationException, IllegalAccessException, UnknownHostException, SocketException {
		this.marshallingService = Factory.current().getInstance(MarshallingService.class);
		this.destinationAddress = ipAddress;
		this.destinationPort = providerPort;
		this.receiverBuffer = receiverBuffer;
		this.senderBuffer = senderBuffer;
		this.socket = new DatagramSocket();
		this.receiver = new ForwardingSenderReceiver(this.receiverBuffer, this.socket);
	}

	@Override
	public void run() {

		new Thread(this.receiver).start();

		while (true) {
			try {
				if (senderBuffer.hasElements()) {
					ReceivedPacket packet = senderBuffer.getFirst();
					//BrokerMessage brokerMessage = new BrokerMessage(packet.getAdress(), packet.getPort());
					//brokerMessage.setMessage();

					String message = packet.getMessage();
					
					byte[] sendData = new byte[MAX_MESSAGE_SIZE];
					sendData = message.getBytes();
					//System.out.println(sendData);
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destinationAddress,
							destinationPort);
					socket.send(sendPacket);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class ForwardingSenderReceiver implements Runnable {

		private DatagramSocket socket;
		private Buffer<ReceivedPacket> receiverBuffer;

		public ForwardingSenderReceiver(Buffer<ReceivedPacket> receiverBuffer, DatagramSocket socket)
				throws SocketException {
			this.receiverBuffer = receiverBuffer;
			this.socket = socket;
		}

		@Override
		public void run() {
			while (true) {
				byte[] receiveData = new byte[MAX_MESSAGE_SIZE];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					// get incoming message
					this.socket.receive(receivePacket);
					String message = new String(receivePacket.getData()).substring(0, receivePacket.getLength());
					BrokerMessage brokerMessage = marshallingService.deSerialize(message, BrokerMessage.class);
					ReceivedPacket packet = new ReceivedPacket();
					packet.setAdress(brokerMessage.getSourceAddress());
					packet.setPort(brokerMessage.getSourcePort());
					packet.setMessage(marshallingService.serialize(brokerMessage.getMessage()));
					receiverBuffer.add(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
