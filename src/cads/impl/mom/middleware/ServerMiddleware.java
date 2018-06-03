
package cads.impl.mom.middleware;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cads.impl.mom.IBuffer;
import cads.impl.mom.Middleware;
import cads.impl.os.Server;
import cads.impl.os.UDPServer;

public class ServerMiddleware extends Middleware {

	private Server<String> server;

	public ServerMiddleware(IBuffer<String> buffer, Server<String> server)
			throws SocketException, UnknownHostException {
		super(buffer);
		this.server = server;
		//new Thread(this).start();
	}
	
	public ServerMiddleware(IBuffer<String> buffer, DatagramSocket socket)
			throws SocketException, UnknownHostException {
		super(buffer);
		this.server = new UDPServer(socket);
		//new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				recieveNextMessage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void recieveNextMessage() throws JsonParseException, JsonMappingException, IOException {
		String msg = new String(server.receive());
		if (!msg.isEmpty() && null != msg) {
			buffer.add(msg);
		}
	}

}
