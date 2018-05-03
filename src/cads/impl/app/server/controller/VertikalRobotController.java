package cads.impl.app.server.controller;

import java.util.Observable;

import cads.impl.hal.IVertikalMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.buffer.Message;

public class VertikalRobotController implements RobotController {

	private IBuffer<Message> buffer;
	private IVertikalMotor motor;

	public VertikalRobotController(IBuffer<Message> buffer, IVertikalMotor motor) {
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
