package cads.impl.hal;
/**
 * The gripper motor controls the gripper statement
 * and can open it or close it.
 * @author AI
 */
public interface IGripperMotor {

	/**
	 * opens gripper if value true, otherwise closes
	 * @param value
	 */
	public void grab(boolean value);
}
