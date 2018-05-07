package cads.impl.app.server.listener;

import java.util.Observable;

public class ObservableValue<T> extends Observable {

	public enum ValueType {
		VERTIKAL, HORIZONTAL, GRIPPER, WATCHDOG
	}

	private T value;
	private ValueType valueType;

	public ObservableValue(T value) {
		super();
		this.value = value;
	}

	public ObservableValue(T value, ValueType valueType) {
		this(value);
		this.valueType = valueType;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
		setChanged();
		notifyObservers();
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}
}
