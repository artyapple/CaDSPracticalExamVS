package cads.impl.hal.server;

import java.util.Observable;
import java.util.Observer;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.impl.app.server.listener.ObservableValue;
import cads.impl.app.server.listener.ObservableValue.ValueType;
import cads.impl.factory.Factory;
import cads.impl.hal.IGripperMotor;

public class GripperMotor implements IGripperMotor, Observer {

	private CaDSEV3RobotHAL robot;
	private boolean currentValue;

	private volatile boolean eStop = false;

	public GripperMotor() throws InstantiationException, IllegalAccessException {
		this.robot = Factory.current().getInstance(CaDSEV3RobotHAL.class);
	}

	@Override
	public void grab(boolean toClose) {
		if (toClose) {
			robot.doClose();
		} else {
			robot.doOpen();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void update(Observable o, Object arg) {
		ObservableValue preCast = (ObservableValue) o;
		if (preCast.getValueType() == ValueType.GRIPPER) {
			ObservableValue<Boolean> currentObservable = (ObservableValue<Boolean>) preCast;
			currentValue = currentObservable.getValue();
			
		} else if (preCast.getValueType() == ValueType.WATCHDOG) {
			ObservableValue<Boolean> currentBooleanObservable = (ObservableValue<Boolean>) preCast;
			if (currentBooleanObservable.getValue() == false) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
