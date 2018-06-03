package cads.impl.mom.buffer;

import org.json.simple.JSONObject;

import cads.impl.mom.IDeSerializable;
import cads.impl.rpc.data.ServiceFunction;
import cads.impl.rpc.data.ServiceFunctionParameter;

public class ServiceMessage implements IDeSerializable<ServiceMessage> {

	private ServiceFunction function;

	public ServiceMessage() {
	}

	public ServiceMessage(ServiceFunction function) {
		this.function = function;
	}

	public ServiceFunction getFunction() {
		return function;
	}

	public void setFunction(ServiceFunction function) {
		this.function = function;
	}

	public String stringValueFor(String parameterName) {
		String result = "";

		for (ServiceFunctionParameter parameter : function.getParameters()) {
			if (parameter.getName().equals(parameterName)) {
				return parameter.getValue();
			}
		}

		return result;
	}

	public int intValueFor(String parameterName) {
		int result = 0;

		String strResult = stringValueFor(parameterName);
		try {
			result = Integer.parseInt(strResult);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	public boolean booleanValueFor(String parameterName) {
		boolean result = false;

		String strResult = stringValueFor(parameterName);
		try {
			result = Boolean.parseBoolean(strResult);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}

		return result;
	}

	@Override
	public ServiceMessage deSerialize(JSONObject jsonObject) throws Exception {
		function = new ServiceFunction().deSerialize((JSONObject) jsonObject.get("function"));
		return this;
	}
}
