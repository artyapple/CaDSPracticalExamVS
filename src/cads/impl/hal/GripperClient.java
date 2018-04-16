package cads.impl.hal;

import cads.impl.app.GripperService;
import cads.impl.mom.IBuffer;
import cads.impl.mom.IMessage;
import cads.impl.mom.Message;
import cads.impl.mom.MessageBuffer;
import cads.impl.mom.Message.MsgType;

public class GripperClient implements GripperMotor {

	IMessage msg;
	IBuffer<Message> buffer = new MessageBuffer<>();
	GripperService gripperService = new GripperService();
	
	@Override
	public void open() {
		IMessage msg = new Message(MsgType.GRIPPER, 1, 100, 0);
		 buffer.addMessage((Message)msg);
		 gripperService.send(buffer);
	}

	@Override
	public void close() {
		IMessage msg = new Message(MsgType.GRIPPER, 1, 0, 0);
		buffer.addMessage((Message)msg);
	}

}
