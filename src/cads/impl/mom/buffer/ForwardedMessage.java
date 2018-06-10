package cads.impl.mom.buffer;

import java.util.UUID;

public class ForwardedMessage {
	
	private UUID transactionId;
	private String message;
	
	public UUID getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(UUID transactionId) {
		this.transactionId = transactionId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
