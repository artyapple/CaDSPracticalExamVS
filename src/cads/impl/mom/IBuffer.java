package cads.impl.mom;

public interface IBuffer<T> {
	public void addMessage(T message);
	public T getLastMessage();
	public boolean hasMessages();
}
