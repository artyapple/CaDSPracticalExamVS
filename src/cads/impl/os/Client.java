package cads.impl.os;

import java.net.InetAddress;

public interface Client<T> {
	public void send(T message);
	public void send(String msg, int destport, InetAddress destip);
	public void stop();
}
