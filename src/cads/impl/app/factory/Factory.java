//package cads.impl.app.factory;
//
//
//import java.util.HashMap;
//
//public class Factory {
//	
//	private static Factory factory;
//	private HashMap<String, > _instances;
//	
//	public Factory() {
//		_instances = new HashMap<String, RegisteredType>();
//	}
//	
//	public void registerType(Class<? extends Object> item) {
//		registerType(item,item);
//	}
//	
//	public <T> void registerType(Class<? extends Object> interfaceItem, Class<? extends T> item) {
//		String key = interfaceItem.getTypeName();
//		if(!_instances.containsKey(key)){
//			RegisteredType type = new RegisteredType();
//			type.setType(item);
//			_instances.put(key, type);
//		}		
//	}
//	
//	public <T> void registerInstance(T obj, Class<? extends T> clazz) {
//		String key = clazz.getTypeName();
//		if(!_instances.containsKey(key)){
//			RegisteredType type = new RegisteredType();
//			type.setType(clazz);
//			type.setObject(obj);
//			 _instances.put(key, type);
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public <T> T getInstance(Class<? extends Object> clazz) throws InstantiationException, IllegalAccessException {
//		T instance = null;
//		String key = clazz.getTypeName();
//		synchronized (this) {
//			if(_instances.containsKey(key)){
//				RegisteredType item = _instances.get(key);
//				Object obj = item.getObject();
//				if(obj == null){
//					obj = item.getType().newInstance();
//					item.setObject(obj);
//				}
//				instance = (T)obj;
//			}			
//		}
//		
//		return instance;
//	}
//	
//	public static Factory current() {
//		synchronized (Factory.class) {
//			if(factory == null){
//				factory = new Factory();
//			}			
//		}
//		
//		return factory;
//	}
//}
