package cads.impl.hal.client;

import cads.impl.hal.IGripperMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.buffer.Message;
import cads.impl.mom.buffer.Message.MsgType;

public class GripperMotor implements IGripperMotor {

	private IBuffer<Message> buffer;

	public GripperMotor(IBuffer<Message> buffer){
		this.buffer = buffer;
	}

	@Override
	public void open(boolean value) {
		Message message = new Message(MsgType.GRIPPER, 1, (value ? 1 : 0), 0);
		buffer.add(message);
	}
}
