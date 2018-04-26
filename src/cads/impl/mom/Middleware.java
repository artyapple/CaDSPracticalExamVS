package cads.impl.mom;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class Middleware implements Runnable {

	protected MessageBuffer<Message> buffer;
	protected MarshallingService ms;
	protected AtomicInteger seq = new AtomicInteger();

	// Runnable
	public Middleware(MessageBuffer<Message> buffer) throws SocketException, UnknownHostException {
		this.buffer = buffer;
		this.ms = new MarshallingService();
		// factory ist gut
	}
	public synchronized boolean hasMessages() {
		return buffer.hasMessages();
	}
	@Override
	public abstract void run();
}
