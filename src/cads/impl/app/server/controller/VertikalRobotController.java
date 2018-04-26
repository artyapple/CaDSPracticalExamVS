package cads.impl.app.server.controller;

import cads.impl.hal.IVertikalMotor;
import cads.impl.mom.Message;
import cads.impl.mom.MessageBuffer;

public class VertikalRobotController implements RobotController {

	private MessageBuffer<Message> buffer;
	private IVertikalMotor motor;
	
	public VertikalRobotController(MessageBuffer<Message> buffer, IVertikalMotor motor) {
		this.buffer = buffer;
		this.motor = motor;
	}
	@Override
	public void move() {
		if(buffer.hasMessages()){
			Message m = buffer.getLastMessage();
			motor.move(m.getValue());
		}
	}
	@Override
	public void run() {
		while(true){
			move();
		}
	}
}
