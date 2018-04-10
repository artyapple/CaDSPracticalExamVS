package cads.impl.communication;

public interface Client<T> extends Runnable{
	public void send(T message);
	public void stop();
}
