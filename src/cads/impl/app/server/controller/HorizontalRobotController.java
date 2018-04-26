package cads.impl.app.server.controller;

import cads.impl.hal.IHorizontalMotor;
import cads.impl.mom.Message;
import cads.impl.mom.MessageBuffer;

public class HorizontalRobotController implements RobotController {

	private MessageBuffer<Message> buffer;
	private IHorizontalMotor motor;

	public HorizontalRobotController(MessageBuffer<Message> buffer, IHorizontalMotor motor) {
			this.buffer = buffer;
			this.motor = motor;
		}

	@Override
	public void move() {
		if (buffer.hasMessages()) {
			Message m = buffer.getLastMessage();
			motor.move(m.getValue());
		}
	}

	@Override
	public void run() {
		while (true) {
			move();
		}
	}

}
