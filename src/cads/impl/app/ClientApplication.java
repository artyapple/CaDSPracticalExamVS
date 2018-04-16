package cads.impl.app;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;
import org.cads.ev3.rmi.consumer.ICaDSRMIConsumer;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveGripper;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveHorizontal;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIMoveVertical;
import org.cads.ev3.rmi.generated.cadSRMIInterface.IIDLCaDSEV3RMIUltraSonic;

public class ClientApplication {
	
	//static CaDSRobotGUISwing gui;

	
	
	
	public static void main(String[] args) {
//		IIDLCaDSEV3RMIMoveGripper gripper = null;
//		ICaDSRMIConsumer consumer = null;
//		IIDLCaDSEV3RMIMoveVertical vertical = null;
//		IIDLCaDSEV3RMIMoveHorizontal horizontal = null;
//		IIDLCaDSEV3RMIUltraSonic ultraSonic = null;
//		
//		gui = new CaDSRobotGUISwing(consumer, gripper, vertical, horizontal, ultraSonic);
//		int refreshRate = 0;
//		gui.startGUIRefresh(refreshRate);
//		
//		//...
//		
//		gui.addService("Robot-1");
		
		ClientView view = new ClientView();
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setSize(350,100);
		view.setVisible(true);
		
		ClientController clientController = new ClientController(view);
		view.setController(clientController);
	
	}

}
