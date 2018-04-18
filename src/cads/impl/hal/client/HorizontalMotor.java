package cads.impl.hal.client;

import cads.impl.hal.IHorizontalMotor;
import cads.impl.mom.Message;
import cads.impl.mom.Message.MsgType;
import cads.impl.mom.MessageBuffer;

public class HorizontalMotor implements IHorizontalMotor {
	
	private MessageBuffer<Message> horizontalMessageBuffer;

	public HorizontalMotor(MessageBuffer<Message> horizontalMessageBuffer) {
		this.horizontalMessageBuffer = horizontalMessageBuffer;
	}

	@Override
	public void move(int value) {
		Message message = new Message(MsgType.HORIZONTAL, 1, value, 0);
		horizontalMessageBuffer.addMessage(message);
	}

}
