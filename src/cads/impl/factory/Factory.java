package cads.impl.factory;

import java.util.HashMap;

public class Factory implements IFactory {

	private static Factory factory;
	private HashMap<String, RegisteredType> _instances;

	public Factory() {
		_instances = new HashMap<String, RegisteredType>();
	}

	public void registerType(Class<? extends Object> item) {
		registerType(item, item);
	}

	public <T> void registerType(Class<? extends Object> interfaceItem, Class<? extends T> item) {
		String key = interfaceItem.getName();
		if (!_instances.containsKey(key)) {
			RegisteredType type = new RegisteredType();
			type.setType(item);
			_instances.put(key, type);
		}
	}

	public <T> void registerType(Class<? extends Object> interfaceItem, Class<? extends T> item, String key) {
		if (!_instances.containsKey(key)) {
			RegisteredType type = new RegisteredType();
			type.setType(item);
			type.setInterface(interfaceItem);
			_instances.put(key, type);
		}
	}

	public <T> void registerInstance(T obj, Class<? extends T> clazz) {
		String key = clazz.getName();
		registerInstance(obj, key);
	}

	public <T> void registerInstance(T obj, String key) {
		if (!_instances.containsKey(key)) {
			RegisteredType type = new RegisteredType();
			type.setObject(obj);
			type.setInterface(obj.getClass());
			_instances.put(key, type);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getInstance(Class<? extends T> clazz) throws InstantiationException, IllegalAccessException {
		T instance = null;
		String key = clazz.getName();
		synchronized (this) {
			if (_instances.containsKey(key)) {
				RegisteredType item = _instances.get(key);
				Object obj = item.getObject();
				if (obj == null) {
					obj = item.getType().newInstance();
					item.setObject(obj);
				}
				instance = (T) obj;
			}
		}

		return instance;
	}

	@SuppressWarnings("unchecked")
	public <T> T getInstance(String key) throws InstantiationException, IllegalAccessException {
		T instance = null;
		synchronized (this) {
			if (_instances.containsKey(key)) {
				RegisteredType item = _instances.get(key);
				Object obj = item.getObject();
				if (obj == null) {
					obj = item.getType().newInstance();
					item.setObject(obj);
				}
				instance = (T) obj;
			}
		}

		return instance;
	}

	public static Factory current() {
		synchronized (Factory.class) {
			if (factory == null) {
				factory = new Factory();
			}
		}

		return factory;
	}
}
