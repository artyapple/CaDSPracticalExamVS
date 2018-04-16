package cads.impl.mom;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cads.impl.os.Client;
import cads.impl.os.Server;

public class MessageHandler implements Runnable {

	private MessageBuffer<Message> buffer;
	private MarshallingService ms;
	private Client<String> client;
	private Server<String> server;
	AtomicInteger seq = new AtomicInteger();

	// Runnable
	public MessageHandler(MessageBuffer<Message> buffer, Client<String> client, Server<String> server) throws SocketException, UnknownHostException  {
		this.buffer = buffer;
		this.ms = new MarshallingService();
		this.client = client;
		this.server = server;
		//factory ist gut
		new Thread(this).start();
	}
	
	public synchronized void sendNextMessage() {
		Message message = buffer.getLastMessage();
		
		message.setSeqId(seq.incrementAndGet());
		String serMsg = null;
		try {
			serMsg = ms.serialize(message);
		} catch (JsonProcessingException e) {
			Logger.getLogger(MessageHandler.class.getName()).log(Level.WARNING, "Serialization failed. Message id:"
					+ message.getSeqId() + "; type: " + message.getType() + "; value" + message.getValue());
		}
		client.send(serMsg);
	}

	public synchronized void recieveNextMessage() throws JsonParseException, JsonMappingException, IOException {
		String msg = new String(server.receive());
		
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
				//too late
			}
		}
	}
	
	public synchronized boolean hasMessages(){
		return buffer.hasMessages();
	}

	@Override
	public void run() {
		while(true){
			sendNextMessage();
		}
	}
}
