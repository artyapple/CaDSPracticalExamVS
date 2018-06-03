package cads.impl.rpc.data;

import java.util.ArrayList;
import java.util.List;

public class Service {

	protected String alias;
	protected int port;
	protected String description;
	protected String serviceName;
	private boolean isGenerateable;
	protected List<ServiceFunction> functions;

	public Service() {
		alias = "";
		description = "";
		serviceName = "";
		functions = new ArrayList<ServiceFunction>();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ServiceFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(List<ServiceFunction> functions) {
		this.functions = functions;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public boolean getIsGenerateable() {
		return isGenerateable;
	}

	public void setIsGenerateable(boolean isGenerateable) {
		this.isGenerateable = isGenerateable;
	}
}
