
package cads.impl.mom;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import cads.impl.os.Server;

public class ServerMiddleware extends Middleware {

	private Server<String> server;

	public ServerMiddleware(MessageBuffer<Message> buffer, Server<String> server)
			throws SocketException, UnknownHostException {
		super(buffer);
		this.server = server;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				recieveNextMessage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void recieveNextMessage() throws JsonParseException, JsonMappingException, IOException {
		String msg = new String(server.receive());
		//to test
		System.out.println("msg rec");
		System.out.println(msg);
		//to test end
		if (!msg.isEmpty() && null != msg) {
			Message message = null;
			message = ms.deSerialize(msg, Message.class);

			if (message.getSeqId() == seq.getAndIncrement()) {
				// duplicate
			} else if (message.getSeqId() == seq.get()) {
				buffer.addMessage(message);
			} else if (message.getSeqId() > seq.get()) {
				// packer missing - logger
				seq.set(message.getSeqId());
			} else {
				// too late
			}
		}
	}

}
