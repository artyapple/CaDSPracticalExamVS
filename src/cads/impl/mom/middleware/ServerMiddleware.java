
package cads.impl.mom;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import cads.impl.os.Server;
import cads.impl.os.UDPClient;

public class ServerMiddleware extends Middleware {

	private Server<String> server;
	
	public ServerMiddleware(IBuffer<Message> buffer, Server<String> server)
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
				e.printStackTrace();
			}
		}
	}

	public synchronized void recieveNextMessage() throws JsonParseException, JsonMappingException, IOException {
		String msg = new String(server.receive());
		
		
		if (!msg.isEmpty() && null != msg) {
			Message message = null;
			message = ms.deSerialize(msg, Message.class);
			if (message.getSeqId() == seq.getAndIncrement()) {
				Logger.getLogger(ServerMiddleware.class.getName()).log(Level.WARNING,
						"The server received and ignored duplicate of the message with seq id: "+message.getSeqId());
			} else if (message.getSeqId() == seq.get()) {
				buffer.add(message);
				Logger.getLogger(ServerMiddleware.class.getName()).log(Level.INFO, "Message received successfully:\n type: " + message.getType() +" seq id: "+ message.getSeqId());
			} else if (message.getSeqId() > seq.get()) {
				Logger.getLogger(ServerMiddleware.class.getName()).log(Level.WARNING,
						"Messages with id from "+seq.get()+" to "+(message.getSeqId()-1) +" were lost");
				buffer.add(message);
				seq.set(message.getSeqId());
			} else {
				Logger.getLogger(ServerMiddleware.class.getName()).log(Level.WARNING,
						"The server received and ignored the message with seq id: "+message.getSeqId()+" too late.");
			}
		}
	}

}
