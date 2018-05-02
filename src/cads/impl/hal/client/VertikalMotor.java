package cads.impl.hal.client;

import cads.impl.hal.IVertikalMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.Message;
import cads.impl.mom.Message.MsgType;

public class VertikalMotor implements IVertikalMotor {

	private IBuffer<Message> buffer;
	
	public VertikalMotor(IBuffer<Message> buffer){
		this.buffer = buffer;
	}
	
	@Override
	public void move(int value) {
		Message msg = new Message(MsgType.VERTIKAL, 1, value, 0);
		buffer.add(msg);
	}

}
