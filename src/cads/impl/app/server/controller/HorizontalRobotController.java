package cads.impl.app.server.controller;

import java.util.Observable;

import cads.impl.hal.IHorizontalMotor;
import cads.impl.mom.Message;
import cads.impl.mom.Buffer;

public class HorizontalRobotController implements RobotController {

	private Buffer<Message> buffer;
	private IHorizontalMotor motor;

	public HorizontalRobotController(Buffer<Message> buffer, IHorizontalMotor motor) {
		this.buffer = buffer;
		this.motor = motor;
	}

	@Override
	public void execute() {
		if (buffer.hasElements()) {
			Message m;
			m = buffer.getLast();
			motor.move(m.getValue());
		}
	}

	@Override
	public void run() {
		while (true) {
			execute();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
