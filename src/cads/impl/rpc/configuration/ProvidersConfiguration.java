package cads.impl.rpc.configuration;

import java.util.ArrayList;
import java.util.List;

import cads.impl.rpc.data.Provider;

public class ProvidersConfiguration {

	private List<Provider> providers;
	
	public ProvidersConfiguration(){
		providers = new ArrayList<>();
	}
	
	public ProvidersConfiguration(List<Provider> providers){
		this();
		setProviders(providers);
	}

	public List<Provider> getProviders() {
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}
}
