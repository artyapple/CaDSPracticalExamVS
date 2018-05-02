package cads.impl.mom;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class Middleware implements Runnable {

	protected IBuffer<Message> buffer;
	protected MarshallingService ms;
	protected AtomicInteger seq = new AtomicInteger();

	// Runnable
	public Middleware(IBuffer<Message> buffer) throws SocketException, UnknownHostException {
		this.buffer = buffer;
		this.ms = new MarshallingService();
		// factory ist gut
	}
	@Override
	public abstract void run();
}
