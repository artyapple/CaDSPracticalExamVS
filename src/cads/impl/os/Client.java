package cads.impl.os;

public interface Client<T> {
	public void send(T message);
	public void stop();
}
