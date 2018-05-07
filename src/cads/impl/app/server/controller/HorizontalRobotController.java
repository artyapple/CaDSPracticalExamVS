package cads.impl.app.server.controller;

import cads.impl.hal.IHorizontalMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.buffer.Message;

public class HorizontalRobotController implements RobotController {

	private IBuffer<Message> buffer;
	private volatile IHorizontalMotor motor;
	private Thread motorThread;

	public HorizontalRobotController(IBuffer<Message> buffer, IHorizontalMotor motor) {
		this.buffer = buffer;
		this.motor = motor;
		motorThread = new Thread((Runnable) motor);
		motorThread.start();
	}

	@Override
	public void execute() {
		if (buffer.hasElements()) {
			Message m;
			m = buffer.getLast();
			if (m != null) {
				motor.move(m.getValue());
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			execute();
		}
	}
}
