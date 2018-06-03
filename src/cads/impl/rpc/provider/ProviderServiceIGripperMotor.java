package cads.impl.rpc.provider;

import java.io.IOException;
import java.net.SocketException;

import cads.impl.soa.buffer.Buffer;
import cads.impl.soa.communication.BrokerReciever;
import cads.impl.soa.communication.IReceiver;
import cads.impl.soa.data.ServiceFunction;
import cads.impl.soa.messages.ServiceMessage;
import cads.impl.factory.Factory;
import cads.impl.shared.MarshallingService;
import cads.impl.soa.messages.ForwardedMessage;

import cads.impl.hal.IGripperMotor;

public class ProviderServiceIGripperMotor implements Runnable {

	/** 
	 * if this size will be more than 1, then the whole message buffer will must be read 
	 * and only the most actual task should be handled, others must be deleted.
	 */
	private final static int MAX_BUFFER_SIZE = 1;
	
	private final static int MAX_MESSAGE_SIZE = 1024;
	private IGripperMotor service;
	private Buffer<ForwardedMessage> messageBuffer;
	private MarshallingService marshallingService;
	private int servicePort;

	public ProviderServiceIGripperMotor(int servicePort) {
		this.servicePort = servicePort;
	}

	@Override
	public void run() {
		try {
			Factory fac = Factory.current();
			messageBuffer = new Buffer<ForwardedMessage>(MAX_BUFFER_SIZE);
			marshallingService = fac.getInstance(MarshallingService.class);
			service = fac.getInstance(IGripperMotor.class);

			// start receiver
			IReceiver<String> receiver = new BrokerReciever(servicePort, false, MAX_MESSAGE_SIZE, messageBuffer);
			new Thread(receiver).start();
			
		} catch (InstantiationException | IllegalAccessException | SocketException e) {
			e.printStackTrace();
		}		
		
		while (true) {
			try {
				ForwardedMessage fwdMessage = messageBuffer.getFirst();
				String strMessage = fwdMessage.getMessage();
				ServiceMessage message = marshallingService.deSerialize(strMessage, ServiceMessage.class);
				ServiceFunction function = message.getFunction();
				switch (function.getName()) {
					case "grab": 
service.grab(
message.booleanValueFor("toClose"));
break;
					default:
						System.out.println("ProviderServiceIGripperMotor: " + "can not handle " + function.getName() + " function");
						break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

}
