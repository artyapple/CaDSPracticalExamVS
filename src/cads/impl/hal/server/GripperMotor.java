package cads.impl.hal.server;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;

import cads.impl.app.server.listener.ObservableValue;
import cads.impl.hal.IGripperMotor;
import cads.impl.os.UDPClient;

public class GripperMotor implements IGripperMotor, Observer {

	private CaDSEV3RobotHAL robot;
	private boolean currentValue;

	public GripperMotor() {
		this.robot = CaDSEV3RobotHAL.getInstance();
	}
	
	@Override
	public void open(boolean value) {
		if (currentValue!=value) {
			if(value){
				robot.doOpen();
			}else{
				robot.doClose();
			}	
		} else {
			Logger.getLogger(UDPClient.class.getName()).log(Level.WARNING,
					"The Gripper is already "+ (value ?"open":"closed")+ ". Ignore command.");
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		ObservableValue<Boolean> currentObservable = (ObservableValue<Boolean>) o;
		currentValue = currentObservable.getValue();
	}
}
