//package cads.impl.app;
//
//import org.cads.ev3.middleware.CaDSEV3RobotHAL;
//import org.cads.ev3.middleware.CaDSEV3RobotType;
//
//public class ServerApplication {
//	
//	private static CaDSEV3RobotHAL caller = null;
//	
//	public static void main(String[] args) {
//		try {
//			Listener simuListener;
//			simuListener = new Listener(caller, CaDSEV3RobotType.SIMULATION);
//			Thread t1 = new Thread(simuListener);
//			t1.start();
//			t1.join();
//		} catch (Exception e) {
//		}
//
//	}
//	
//}
//
