package cads.impl.rpc.configuration;

import java.util.List;

import cads.impl.rpc.data.Service;

public class ServicesConfiguration {
	
	private String alias;
	private List<Service> services;

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
