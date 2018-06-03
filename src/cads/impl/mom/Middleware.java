package cads.impl.mom;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class Middleware implements Runnable {

	protected IBuffer<String> buffer;
	protected AtomicInteger seq = new AtomicInteger();

	// Runnable
	public Middleware(IBuffer<String> buffer) {
		this.buffer = buffer;
		// factory ist gut
	}

	@Override
	public abstract void run();
}
