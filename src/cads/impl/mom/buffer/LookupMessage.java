package cads.impl.mom.buffer;

import java.util.List;

import cads.impl.rpc.data.Provider;


public class LookupMessage {

	private List<Provider> providers;

	public List<Provider> getProviders() {
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}
}
