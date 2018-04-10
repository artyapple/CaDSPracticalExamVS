package cads.impl.communication;

public interface Server<T> extends Runnable {
	public static final int BUFFER_SIZE = 1024;
	public void receive();
	public void stop();
}
