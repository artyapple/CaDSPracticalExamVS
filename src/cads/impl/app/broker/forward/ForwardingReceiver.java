package cads.impl.app.broker.forward;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cads.impl.app.broker.data.ReceivedPacket;
import cads.impl.mom.buffer.Buffer;

public class ForwardingReceiver implements Runnable {

	private final int MAX_MESSAGE_SIZE = 1024;

	private DatagramSocket socket;
	private Buffer<ReceivedPacket> senderBuffer;
	private Buffer<ReceivedPacket> receiverBuffer;

	private ForwardingReceiverSender receiverSender;
	private int port;

	public ForwardingReceiver(int port, Buffer<ReceivedPacket> receiverBuffer, Buffer<ReceivedPacket> senderBuffer) throws SocketException {
		this.port = port;
		this.senderBuffer = senderBuffer;
		this.receiverBuffer = receiverBuffer;
		this.socket = new DatagramSocket(this.port);
		this.receiverSender = new ForwardingReceiverSender(this.socket, this.receiverBuffer);
	}

	@Override
	public void run() {
		new Thread(this.receiverSender).start();
		while (true) {
			byte[] receiveData = new byte[MAX_MESSAGE_SIZE];
			DatagramPacket datagram = new DatagramPacket(receiveData, receiveData.length);
			try {
				socket.receive(datagram);
				String message = new String(datagram.getData());

				ReceivedPacket packet = new ReceivedPacket();
				packet.setAdress(datagram.getAddress());
				packet.setPort(datagram.getPort());
				packet.setMessage(message);

				senderBuffer.add(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class ForwardingReceiverSender implements Runnable {

		private Buffer<ReceivedPacket> receiverBuffer;
		private DatagramSocket socket;

		public ForwardingReceiverSender(DatagramSocket socket, Buffer<ReceivedPacket> receiverBuffer) {
			this.socket = socket;
			this.receiverBuffer = receiverBuffer;
		}

		@Override
		public void run() {
			while (true) {
				if(receiverBuffer.hasElements()){
					ReceivedPacket packet = receiverBuffer.getFirst();
					try {
						String sendMessage = packet.getMessage();
						byte[] sendData = new byte[MAX_MESSAGE_SIZE];
						sendData = sendMessage.getBytes();
						System.out.println("Broker rec: "+sendData);
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAdress(), packet.getPort());
						socket.send(sendPacket);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
	}
}
