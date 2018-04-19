package cads.impl.app.server.listener;

import java.util.Observable;

public class ObservableValue<T> extends Observable {
	
	private T value;

	public ObservableValue(T value) {
		super();
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
		setChanged();
		notifyObservers();
	}	
}
