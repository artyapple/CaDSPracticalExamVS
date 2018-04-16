package cads.impl.app;

import cads.impl.hal.BaseMotor;

public class HorizontalMoveService {
	
	private int port = 80;
	private String dest_ip = "0.0.0.0";
	
	//------------------------
	private BaseMotor motor;
	
	
	
	public void move (int value) {
		motor.move(value);
	}
}
