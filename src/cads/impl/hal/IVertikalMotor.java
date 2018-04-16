package cads.impl.hal;
/**
 * The joint motor controls the vertical movement (top, down).
 * @author AI
 */
public interface JointMotor {
	/**
	 * moves vertically to the specified value
	 * @param value
	 */
	public void move(int value);
}
