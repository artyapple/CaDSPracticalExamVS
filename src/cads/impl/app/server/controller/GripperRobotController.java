package cads.impl.app.server.controller;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import cads.impl.hal.IGripperMotor;
import cads.impl.mom.buffer.Buffer;
import cads.impl.mom.buffer.Message;

public class GripperRobotController implements RobotController {

	private boolean inWork;
	private Buffer<Message> buffer;
	private IGripperMotor motor;

	public GripperRobotController(Buffer<Message> buffer, IGripperMotor motor) {
		this.buffer = buffer;
		this.motor = motor;
	}

	@Override
	public void run() {
		while (true) {
			execute();
		}
	}

	@Override
	public void execute() {
		if (buffer.hasElements()) {
			Message m = buffer.getLast();
			if (m.getValue() == 1) {
				motor.open(true);
			} else if (m.getValue() == 0) {
				motor.open(false);
			} else {
				Logger.getLogger(GripperRobotController.class.getName()).log(Level.WARNING, "Gripper value is wrong");
			}

		}
	}
}
