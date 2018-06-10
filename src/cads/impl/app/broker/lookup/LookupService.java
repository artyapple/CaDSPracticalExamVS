package cads.impl.app.broker.lookup;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import cads.impl.app.broker.data.RegisteredProviders;
import cads.impl.factory.Factory;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.buffer.LookupMessage;
import cads.impl.rpc.data.Provider;

public class LookupService implements Runnable{

	private final int MAX_MESSAGE_SIZE = 1024;
	
	private boolean isRunning = true;
	private RegisteredProviders registeredProviders;
	private DatagramSocket socket;
	private MarshallingService marshallingService;
	
	public LookupService(int port) 
			throws SocketException, InstantiationException, IllegalAccessException, UnknownHostException{

		this.socket = new DatagramSocket(port);
		this.marshallingService = new MarshallingService();
		this.registeredProviders = Factory.current().getInstance(RegisteredProviders.class);
	}

	public synchronized void stop(){
		isRunning = false;
	}
	
	@Override
	public void run() {
		System.out.println("Starte LookupService");
		
		try{
			while(true){
				byte[] receiveData = new byte[MAX_MESSAGE_SIZE];
				
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				
				socket.receive(receivePacket);
				// Get Incoming Message
				String retMessage = new String(receivePacket.getData())
						.substring(0, receivePacket.getLength());
				System.out.println("Lookup retrieve: " + retMessage);
				
				InetAddress senderAddress = receivePacket.getAddress();
				int senderPort = receivePacket.getPort();
				
				LookupMessage message = new LookupMessage();
				List<Provider> list = registeredProviders.getProvidersForLookup();
				if(list.isEmpty()){System.out.println("List of Providers is empty");}
				message.setProviders(list);

				String strMessage = marshallingService.serialize(message);
				
				DatagramPacket sendPacket = new DatagramPacket(strMessage.getBytes(), strMessage.getBytes().length, senderAddress, senderPort);
				socket.send(sendPacket);
				System.out.println("Lookup send: " + strMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
