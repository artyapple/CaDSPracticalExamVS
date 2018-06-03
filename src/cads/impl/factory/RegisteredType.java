package cads.impl.factory;

public class RegisteredType {

	private Class<? extends Object> _interface;
	private Class<? extends Object> _type;
	private Object _object;
	
	public void setInterface(Class<? extends Object> item){
		_interface = item;
	}
	
	public Class<? extends Object> getInterface(){
		return _interface;
	}
	
	public void setType(Class<? extends Object> item){
		_type = item;
	}
	
	public Class<? extends Object> getType(){
		return _type;
	}
	
	public void setObject(Object obj){
		_object = obj;
	}
	
	public Object getObject(){
		return _object;
	}
	
    @Override
    public boolean equals(Object o) {

        if (o == this) {
        	return true;
        }
        
        if (!(o instanceof RegisteredType)) {
            return false;
        }

        RegisteredType type = (RegisteredType) o;

        return type._type.equals(_type);
    }

    @Override
    public int hashCode() {
        return _type.hashCode();
    }
	
}
