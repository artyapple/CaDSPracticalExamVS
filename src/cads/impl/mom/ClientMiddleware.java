package cads.impl.mom;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import cads.impl.os.Client;

public class ClientMiddleware extends Middleware {

	private Client<String> client;

	public ClientMiddleware(MessageBuffer<Message> buffer, Client<String> client)
			throws SocketException, UnknownHostException {
		super(buffer);
		this.client = client;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(true){
			sendNextMessage();
		}
	}

	public synchronized void sendNextMessage() {
		Message message = buffer.getLastMessage();

		message.setSeqId(seq.incrementAndGet());
		String serMsg = null;
		try {
			serMsg = ms.serialize(message);
		} catch (JsonProcessingException e) {
			Logger.getLogger(ClientMiddleware.class.getName()).log(Level.WARNING, "Serialization failed. Message id:"
					+ message.getSeqId() + "; type: " + message.getType() + "; value" + message.getValue());
		}
		client.send(serMsg);
	}

}
