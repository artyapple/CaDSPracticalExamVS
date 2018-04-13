package cads.impl.mom;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class MessageHandler {

	private IBuffer<Message> buffer;
	private MarshallingService ms;
	AtomicInteger seq = new AtomicInteger();

	public MessageHandler(IBuffer<Message> buffer, MarshallingService ms) {
		this.buffer = buffer;
		this.ms = ms;
	}
	
	public synchronized String getNextMessage() {
		Message message = buffer.getLastMessage();
		message.setSeqId(seq.incrementAndGet());
		try {
			return ms.serialize(message);
		} catch (JsonProcessingException e) {
			Logger.getLogger(MessageHandler.class.getName()).log(Level.WARNING, "Serialization failed. Message id:"
					+ message.getSeqId() + "; type: " + message.getType() + "; value" + message.getValue());
		}
		return "";
	}

	public synchronized void setNextMessage(String msg) throws JsonParseException, JsonMappingException, IOException {
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
}
