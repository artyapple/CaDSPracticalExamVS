package cads.impl.hal.server;

import java.util.Observable;
import java.util.Observer;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.impl.app.server.listener.ObservableValue;
import cads.impl.factory.Factory;
import cads.impl.hal.IHorizontalMotor;

public class HorizontalMotor implements IHorizontalMotor, Observer {

	private enum DirectionHorizonal {
		LEFT, RIGHT, NONE
	}

	private CaDSEV3RobotHAL robot;
	private int currentValue;
	private int targetValue;
	private DirectionHorizonal direction;

	public HorizontalMotor() throws InstantiationException, IllegalAccessException {
		this.robot = Factory.current().getInstance(CaDSEV3RobotHAL.class);
		this.direction = DirectionHorizonal.NONE;
		new Thread(new HRMotor()).start();
	}

	@Override
	public void move(int value) {
		targetValue = value;
		if (targetValue > currentValue || targetValue > currentValue + 1) {
			setMovingDirection(DirectionHorizonal.LEFT);
		} else if (targetValue < currentValue || targetValue < currentValue - 1) {
			setMovingDirection(DirectionHorizonal.RIGHT);
		} else {
			setMovingDirection(DirectionHorizonal.NONE);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("motor update");
		ObservableValue preCast = (ObservableValue) o;

		ObservableValue<Integer> currentObservable = (ObservableValue<Integer>) preCast;
		currentValue = currentObservable.getValue();
		if ((direction == DirectionHorizonal.LEFT && currentValue >= targetValue)
				|| (direction == DirectionHorizonal.RIGHT && currentValue <= targetValue)) {
			setMovingDirection(DirectionHorizonal.NONE);
		}

	}

	private synchronized DirectionHorizonal getMovingDirection() {
		while (direction == DirectionHorizonal.NONE) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return direction;
	}

	private synchronized void setMovingDirection(DirectionHorizonal dir) {
		if (direction != dir || dir == DirectionHorizonal.NONE) {
			robot.stop_h();
		}
		direction = dir;
		notify();
	}
	
	private void moveLeft(){
		robot.moveLeft();
	}
	private void moveRight(){
		robot.moveRight();
	}
	class HRMotor implements Runnable {
		@Override
		public void run() {
			while (true) {
				System.out.println("motor running");
				switch (getMovingDirection()) {
				case LEFT:
					moveLeft();
					break;
				case RIGHT:
					moveRight();
					break;
				default:
					break;
				}
			}
		}
	}
}
