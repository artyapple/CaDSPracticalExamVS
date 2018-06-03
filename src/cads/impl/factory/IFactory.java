package cads.impl.factory;

public interface IFactory {
	
	void registerType(Class<? extends Object> item);
	<T> void registerType(Class<? extends Object> interfaceItem, Class<? extends T> item);

	<T> T getInstance(Class<? extends T> item) throws InstantiationException, IllegalAccessException;
	
}
