package cads.impl.app.broker.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cads.impl.rpc.data.Provider;


public class RegisteredProviders {

	private HashMap<String, BrokerProvider> providers;
	
	public RegisteredProviders(){
		this.providers = new HashMap<>();
	}

	public HashMap<String, BrokerProvider> getProviders() {
		return providers;
	}

	public void setProviders(HashMap<String, BrokerProvider> providers) {
		this.providers = providers;
	}
	
	public boolean addProvider(String key, BrokerProvider provider){
		boolean result = false;
		
		if(!providers.containsKey(key)){
			providers.put(key, provider);
			result = true;
		}
		
		return result;
	}
	
	public List<Provider> getProvidersForLookup(){
		List<Provider> result = new ArrayList<Provider>();
		
		for(BrokerProvider bProvider : providers.values()){
			Provider provider = bProvider.getProvider();
			result.add(provider);
		}
		
		return result;
	}
}
