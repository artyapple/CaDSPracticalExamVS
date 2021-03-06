package cads.impl.os;

import java.io.IOException;

public interface Server<T> {
	public static final int BUFFER_SIZE = 2048;
	public byte[] receive() throws IOException;
	public void stop();
}
