package cads.impl.mom;


public interface IBuffer<T> {
	/**
	 * adds element (according to fifo)
	 * @param message
	 */
	public void add(T element);
	/**
	 * gets element (according to fifo)
	 * @return
	 */
	public T getLast();
	/**
	 * gets element (according to lifo) and clears buffer
	 * @return 
	 */
	public T getFirstAndClear();
	/**
	 * true if buffer has elements, otherwise false
	 * @return
	 */
	public boolean hasElements();
	/**
	 * gets size of this buffer
	 * @return
	 */
	public int size();
}
