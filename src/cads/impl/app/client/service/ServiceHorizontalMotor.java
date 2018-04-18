package cads.impl.app.client.service;

import cads.impl.hal.IHorizontalMotor;
import cads.impl.hal.client.HorizontalMotor;
import cads.impl.mom.Message;
import cads.impl.mom.MessageBuffer;

public class ServiceHorizontalMotor {

	private MessageBuffer<Message> horizontalMessageBuffer;
	private IHorizontalMotor horizontalMotor;
	
	public ServiceHorizontalMotor(MessageBuffer<Message> horizontalMessageBuffer) {
		this.horizontalMessageBuffer = horizontalMessageBuffer;
		this.horizontalMotor = new HorizontalMotor(horizontalMessageBuffer);
	}

	public void move(int percent) {
		horizontalMotor.move(percent);
	}

}
