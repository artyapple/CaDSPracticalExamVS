package cads.impl.hal.client;

import cads.impl.hal.IVertikalMotor;
import cads.impl.mom.IMessage;
import cads.impl.mom.Message;
import cads.impl.mom.Message.MsgType;
import cads.impl.mom.MessageBuffer;

public class VertikalMotor implements IVertikalMotor {

	private MessageBuffer<Message> buffer;
	
	public VertikalMotor(MessageBuffer<Message> buffer){
		this.buffer = buffer;
	}
	
	@Override
	public void move(int value) {
		Message msg = new Message(MsgType.VERTIKAL, 1, value, 0);
		buffer.addMessage(msg);
	}

}
