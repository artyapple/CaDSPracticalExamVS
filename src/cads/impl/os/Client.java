package cads.impl.os;

import java.net.DatagramSocket;

public interface Client<T> {
	public void send(T message);
	public void stop();
	public DatagramSocket getSocket();
}
