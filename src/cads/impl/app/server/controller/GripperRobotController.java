package cads.impl.app.server.controller;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import cads.impl.hal.IGripperMotor;
import cads.impl.mom.Buffer;
import cads.impl.mom.Message;

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
		if (buffer.hasElements() && inWork) {
			Message m = buffer.getLast();
			if (m.getValue() == 1) {
				motor.open(Boolean.TRUE);
			} else if (m.getValue() == 0) {
				motor.open(Boolean.FALSE);
			} else {
				Logger.getLogger(GripperRobotController.class.getName()).log(Level.WARNING, "Gripper value is wrong");
			}

		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
