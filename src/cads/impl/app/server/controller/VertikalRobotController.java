package cads.impl.app.server.controller;

import cads.impl.hal.IVertikalMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.buffer.Message;

public class VertikalRobotController implements RobotController {

	private IBuffer<Message> buffer;
	private volatile IVertikalMotor motor;
	private Thread motorThread;

	public VertikalRobotController(IBuffer<Message> buffer, IVertikalMotor motor) {
		this.buffer = buffer;
		this.motor = motor;
		motorThread = new Thread((Runnable)motor);
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
