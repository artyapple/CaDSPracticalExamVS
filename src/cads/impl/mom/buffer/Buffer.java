package cads.impl.mom.buffer;

import java.util.concurrent.LinkedBlockingDeque;

import cads.impl.mom.IBuffer;

public class Buffer<T> implements IBuffer<T> {

	private static final int DEF_CAPACITY = 1;
	
	private final int capacity;
	private LinkedBlockingDeque<T> deque;
	private volatile boolean empty = true;
	
	/**
	 * @param capacity
	 */
	public Buffer(int capacity) {
		super();
		this.capacity = capacity;
		this.deque = new LinkedBlockingDeque<>(capacity);
	}
	public Buffer() {
		this(DEF_CAPACITY);
	}

	@Override
	public synchronized void add(T message) {

		while (empty == false) { // wait till the buffer becomes empty
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		
		if(deque.size()==capacity){
			deque.clear();
		}
				
		if (message != null) {
			deque.add(message);
		}
		empty = false;
		notifyAll();
	}

	@Override
	public synchronized T getLast() {
		while (empty == true) { // wait till something appears in the buffer
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		empty = true;
		notifyAll();

		return deque.pollFirst();

	}

	@Override
	public synchronized T getFirstAndClear() {
		while (empty == true) { // wait till something appears in the buffer
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		empty = true;
		notifyAll();

		T element = deque.pollLast();
		deque.clear();
		return element;
	}
	
	@Override
	public synchronized boolean hasElements() {
		return !(deque.isEmpty());
	}
	
	@Override
	public synchronized int size(){
		return deque.size();
	}
}
