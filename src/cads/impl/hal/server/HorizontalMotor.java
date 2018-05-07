package cads.impl.hal.server;

import java.util.Observable;
import java.util.Observer;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.impl.app.server.listener.ObservableValue;
import cads.impl.hal.IHorizontalMotor;
import cads.impl.mom.IBuffer;
import cads.impl.mom.buffer.Message;

public class HorizontalMotor implements IHorizontalMotor, Observer, Runnable {

	private enum DirectionHorizonal {
		LEFT, RIGHT, NONE
	}

	private CaDSEV3RobotHAL robot;
	private int currentValue;
	private volatile int targetValue;
	private DirectionHorizonal direction;
	
	public HorizontalMotor(IBuffer<Message> buffer) {
		this.robot = CaDSEV3RobotHAL.getInstance();
	}

	@Override
	public void move(int value) {
		targetValue = value;
	}

	@Override
	public void update(Observable o, Object arg) {
		ObservableValue<Integer> currentObservable = (ObservableValue<Integer>) o;
		currentValue = currentObservable.getValue();
		if ((direction == DirectionHorizonal.LEFT && currentValue >= targetValue)
				|| (direction == DirectionHorizonal.RIGHT && currentValue <= targetValue)) {
			robot.stop_h();
			direction = DirectionHorizonal.NONE;
		}
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			if (targetValue > currentValue && targetValue > currentValue+2) {
				direction = DirectionHorizonal.LEFT;
				robot.moveLeft();
			} else if (targetValue < currentValue && targetValue < currentValue-2) {
				direction = DirectionHorizonal.RIGHT;
				robot.moveRight();
			}
		}	
	}

}
