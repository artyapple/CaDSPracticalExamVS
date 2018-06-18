package cads.impl.hal.server;

import java.util.Observable;
import java.util.Observer;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.impl.app.server.listener.ObservableValue;
import cads.impl.factory.Factory;
import cads.impl.hal.IVertikalMotor;

public class VertikalMotor implements IVertikalMotor, Observer{

	private enum DirectionVertikal {
		UP, DOWN, NONE
	}

	private CaDSEV3RobotHAL robot;
	private int currentValue;
	private volatile int targetValue;
	private DirectionVertikal direction;

	public VertikalMotor() throws InstantiationException, IllegalAccessException {
		this.robot = Factory.current().getInstance(CaDSEV3RobotHAL.class);
		this.direction = DirectionVertikal.NONE;
		new Thread(new VRMotor()).start();
	}

	
	@Override
	public void move(int value) {
		targetValue = value;
		if (targetValue > currentValue || targetValue > currentValue + 1) {
			setMovingDirection(DirectionVertikal.UP);
		} else if (targetValue < currentValue || targetValue < currentValue - 1) {
			setMovingDirection(DirectionVertikal.DOWN);
		} else {
			setMovingDirection(DirectionVertikal.NONE);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void update(Observable o, Object arg) {
		ObservableValue preCast = (ObservableValue) o;

		ObservableValue<Integer> currentObservable = (ObservableValue<Integer>) preCast;
		currentValue = currentObservable.getValue();
		if ((direction == DirectionVertikal.UP && currentValue >= targetValue)
				|| (direction == DirectionVertikal.DOWN && currentValue <= targetValue)) {
			setMovingDirection(DirectionVertikal.NONE);
		}

	}

	private synchronized DirectionVertikal getMovingDirection() {
		while (direction == DirectionVertikal.NONE) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return direction;
	}

	private synchronized void setMovingDirection(DirectionVertikal dir) {
		if (direction != dir || dir == DirectionVertikal.NONE) {
			robot.stop_v();
		}
		direction = dir;
		notify();
	}
	
	private void moveUp(){
		robot.moveUp();
	}
	private void moveDown(){
		robot.moveDown();
	}
	class VRMotor implements Runnable {
		@Override
		public void run() {
			while (true) {
				switch (getMovingDirection()) {
				case UP:
					moveUp();
					break;
				case DOWN:
					moveDown();
					break;
				default:
					break;
				}
			}
		}
	}

}
