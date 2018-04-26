package cads.impl.os;

import java.net.InetAddress;

public interface Server<T> {
	public static final int BUFFER_SIZE = 1024;
	public byte[] receive();
	public void stop();
	public void send(String string, InetAddress byName, int i);
}
