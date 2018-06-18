package cads.impl.rpc.data;

import java.util.ArrayList;
import java.util.List;

public class Provider {
	
	private String alias;
	private String statusChannel;
	private List<Service> services;
	
	public String getStatusChannel() {
		return statusChannel;
	}

	public void setStatusChannel(String statusChannel) {
		this.statusChannel = statusChannel;
	}

	public Provider(){
		this.services = new ArrayList<>();
	}

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
