package cads.impl.hal;
/**
 * The gripper motor controls the gripper statement
 * and can open it or close it.
 * @author AI
 */
public interface IGripperMotor {
	/**
	 * opens gripper
	 */
	public void open();
	/**
	 * closes gripper
	 */
	public void close();
}
