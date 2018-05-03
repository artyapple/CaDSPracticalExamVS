package cads.impl.hal.client;

import cads.impl.hal.IHorizontalMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.buffer.Message;
import cads.impl.mom.buffer.Message.MsgType;

public class HorizontalMotor implements IHorizontalMotor {
	
	private IBuffer<Message> horizontalMessageBuffer;

	public HorizontalMotor(IBuffer<Message> horizontalMessageBuffer) {
		this.horizontalMessageBuffer = horizontalMessageBuffer;
	}

	@Override
	public void move(int value) {
		Message message = new Message(MsgType.HORIZONTAL, 1, value, 0);
		horizontalMessageBuffer.add(message);
	}

}
