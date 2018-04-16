package cads.impl.os;

public interface Server<T> {
	public static final int BUFFER_SIZE = 1024;
	public byte[] receive();
	public void stop();
}
