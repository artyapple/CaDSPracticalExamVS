package cads.impl.rpc.consumer;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;

import cads.impl.factory.Factory;
import cads.impl.hal.IGripperMotor;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.Middleware;
import cads.impl.mom.buffer.Buffer;
import cads.impl.mom.buffer.ServiceMessage;
import cads.impl.mom.middleware.ClientMiddleware;
import cads.impl.rpc.data.ServiceFunction;
import cads.impl.rpc.data.ServiceFunctionParameter;

import cads.impl.hal.IHorizontalMotor;

public class ConsumerControllingServiceIHorizontalMotor implements IHorizontalMotor {
	
	protected MarshallingService marshallingService;
	protected Buffer<String> senderBuffer;
	
	public ConsumerControllingServiceIHorizontalMotor(int brokerPort, String brokerIp) throws InstantiationException, IllegalAccessException, SocketException, UnknownHostException {
		senderBuffer = new Buffer<String>();
		marshallingService = Factory.current().getInstance(MarshallingService.class);

		Middleware sender = new ClientMiddleware(brokerPort, brokerIp, senderBuffer);
		new Thread(sender).start();
	}
	
	@Override
public void move(int percent) {
    putMessageToBuffer(new ServiceMessage(new ServiceFunction("move", Arrays.asList(new ServiceFunctionParameter("int", "percent", String.valueOf(percent))), "void")));
}

	
	/**
	 * Serialized the specified message and put it to the buffer.
	 */
	private void putMessageToBuffer(ServiceMessage message) {
		try {
			String serializedMessage = marshallingService.serialize(message);
			senderBuffer.add(serializedMessage);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
