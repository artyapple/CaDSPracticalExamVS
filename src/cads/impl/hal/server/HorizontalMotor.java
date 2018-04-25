package cads.impl.hal.server;

import java.util.Observable;
import java.util.Observer;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.impl.app.server.listener.ObservableValue;
import cads.impl.hal.IHorizontalMotor;

public class HorizontalMotor implements IHorizontalMotor, Observer {

	
private enum DirectionHorizonal {LEFT, RIGHT, NONE}
	
	private CaDSEV3RobotHAL robot;
	private int currentValue;
	private int targetValue;
	private DirectionHorizonal direction;
	
	public HorizontalMotor(){
		this.robot = CaDSEV3RobotHAL.getInstance();
	}
	
	@Override
	public void move(int value) {
		targetValue = value;
		if (value > currentValue) {
			direction = DirectionHorizonal.LEFT;
			robot.moveLeft();		
		} else if (value < currentValue) {
			direction = DirectionHorizonal.RIGHT;
			robot.moveRight();		
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		ObservableValue<Integer> currentObservable =(ObservableValue<Integer>)o; 
		currentValue = currentObservable.getValue();
		if ((direction == DirectionHorizonal.LEFT && currentValue >= targetValue)
				|| (direction == DirectionHorizonal.RIGHT && currentValue <= targetValue)) {		
			robot.stop_h();
			direction = DirectionHorizonal.NONE;
		}
	}
}
