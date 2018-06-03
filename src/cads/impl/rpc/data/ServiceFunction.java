package cads.impl.rpc.data;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import cads.impl.mom.IDeSerializable;

public class ServiceFunction implements IDeSerializable<ServiceFunction> {

	private String name;
	private List<ServiceFunctionParameter> parameters;
	private String returnType;

	public ServiceFunction () {
		name = "";
		parameters = new ArrayList<ServiceFunctionParameter>();
		returnType = "";
	}

	public ServiceFunction(String name, List<ServiceFunctionParameter> parameters, String returnType) {
		this.name = name;
		this.parameters = parameters;
		this.returnType = returnType;
	}
	
	@Override
	public ServiceFunction deSerialize(JSONObject jsonObject) throws Exception {
		name = (String) jsonObject.get("name");
		returnType = (String) jsonObject.get("returnType");
		
		parameters = new ArrayList<>();
		for (Object parameterObject :(JSONArray)jsonObject.get("parameters")) {
			JSONObject parameterJson = (JSONObject) parameterObject;
			ServiceFunctionParameter parameter = new ServiceFunctionParameter().deSerialize(parameterJson);
			parameters.add(parameter);
		}
		
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceFunctionParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<ServiceFunctionParameter> parameters) {
		this.parameters = parameters;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
}
