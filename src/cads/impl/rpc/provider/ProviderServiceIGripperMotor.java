package cads.impl.rpc.provider;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import cads.impl.factory.Factory;
import cads.impl.hal.IHorizontalMotor;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.buffer.Buffer;
import cads.impl.mom.buffer.ServiceMessage;
import cads.impl.mom.middleware.ServerMiddleware;
import cads.impl.os.UDPServer;
import cads.impl.rpc.data.ServiceFunction;
import cads.impl.hal.IGripperMotor;

public class ProviderServiceIGripperMotor implements Runnable {

	/**
	 * if this size will be more than 1, then the whole message buffer will must
	 * be read and only the most actual task should be handled, others must be
	 * deleted.
	 */
	private final static int MAX_BUFFER_SIZE = 1;
	private IGripperMotor service;
	private Buffer<String> messageBuffer;
	private MarshallingService marshallingService;
	private int servicePort;

	public ProviderServiceIGripperMotor(int servicePort) {
		this.servicePort = servicePort;
	}

	@Override
	public void run() {
		try {
			Factory fac = Factory.current();
			messageBuffer = new Buffer<String>(MAX_BUFFER_SIZE);
			marshallingService = fac.getInstance(MarshallingService.class);
			service = fac.getInstance(IGripperMotor.class);
			// start receiver
			ServerMiddleware receiver = new ServerMiddleware(messageBuffer, new UDPServer(servicePort));
			new Thread(receiver).start();

		} catch (InstantiationException | IllegalAccessException | SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				if (messageBuffer.hasElements()) {
					String fwdMessage = messageBuffer.getFirst();
					System.out.println(fwdMessage);
					if (fwdMessage != null) {
						ServiceMessage message = marshallingService.deSerialize(fwdMessage, ServiceMessage.class);
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
				
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}