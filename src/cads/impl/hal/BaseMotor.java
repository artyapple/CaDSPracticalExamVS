package cads.impl.hal;
/**
 * The base motor controls the horizontal movement (left, right). 
 * @author AI
 */
public interface BaseMotor {
	/**
	 * moves horizontally to the specified value
	 * @param value
	 */
	public void move(int value);
}
