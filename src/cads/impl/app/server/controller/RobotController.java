package cads.impl.app.server.controller;

import java.util.Observer;

public interface RobotController extends Runnable, Observer{
	public void execute();
}
