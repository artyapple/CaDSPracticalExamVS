package cads.impl.hal.server;

import java.util.Observable;
import java.util.Observer;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.impl.app.server.listener.ObservableValue;
import cads.impl.hal.IVertikalMotor;

public class VertikalMotor implements IVertikalMotor, Observer{
	
	private enum DirectionVertikal {UP, DOWN, NONE}
	
	private CaDSEV3RobotHAL robot;
	private int currentValue;
	private int targetValue;
	private DirectionVertikal direction;
	
	public VertikalMotor() {
		this.robot = CaDSEV3RobotHAL.getInstance();
	}
	
	@Override
	public void move(int value) {
		targetValue = value;
		if (value > currentValue) {
			direction = DirectionVertikal.UP;
			robot.moveUp();		
		} else if (value < currentValue) {
			direction = DirectionVertikal.DOWN;
			robot.moveDown();		
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		ObservableValue<Integer> currentObservable =(ObservableValue<Integer>)o; 
		currentValue = currentObservable.getValue();
		
		if ((direction == DirectionVertikal.UP && currentValue >= targetValue)
				|| (direction == DirectionVertikal.DOWN && currentValue <= targetValue)) {		
			robot.stop_v();
			direction = DirectionVertikal.NONE;
		}
	}

}
