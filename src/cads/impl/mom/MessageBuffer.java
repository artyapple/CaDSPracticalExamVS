package cads.impl.mom;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBuffer<T> implements IBuffer<T> {

	private Queue<T> fifo = new ConcurrentLinkedQueue<>();
	private volatile boolean empty = true;

	@Override
	public synchronized void addMessage(T message) {

		while (empty == false) { // wait till the buffer becomes empty
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		if (message != null) {
			fifo.add(message);
		}
		empty = false;
		notifyAll();
	}
	@Override
	public synchronized T getLastMessage() {
		while (empty == true) { // wait till something appears in the buffer
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		empty = true;
		notifyAll();

		if (fifo.isEmpty()) {
			return null;
		} else {
			return fifo.poll();
		}
	}
	@Override
	public boolean hasMessages() {
		return !(fifo.isEmpty());
	}
	
	

}
