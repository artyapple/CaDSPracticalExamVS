package cads.impl.rpc.data;

import org.json.simple.JSONObject;

import cads.impl.mom.IDeSerializable;

public class ServiceFunctionParameter implements IDeSerializable<ServiceFunctionParameter> {

	private String type;
	private String name;
	private String value;

	public ServiceFunctionParameter() {
		type = "";
		name = "";
		value = "";
	}

	public ServiceFunctionParameter(String type, String name, String value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}

	@Override
	public ServiceFunctionParameter deSerialize(JSONObject jsonObject) throws Exception {
		type = (String) jsonObject.get("type");
		name = (String) jsonObject.get("name");
		value = (String) jsonObject.get("value");
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
