package cads.impl.app.broker.data;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import cads.impl.rpc.data.Provider;


public class BrokerProvider {

	private InetAddress ipAddress;
	private Provider provider;
	private List<BrokerService> services;
	
	public BrokerProvider(Provider provider){
		this.setServices(new ArrayList<BrokerService>());
		this.provider = provider;
	}
	
	public List<BrokerService> getServices() {
		return services;
	}

	public void setServices(List<BrokerService> services) {
		this.services = services;
	}
	
	public void addService(BrokerService service) {
		this.services.add(service);
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAlias() {
		return provider != null ? provider.getAlias() : "";
	}
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	@Override
	public boolean equals(Object other) {
		boolean result = false;

	    if ((other instanceof BrokerProvider)){
	    	 BrokerProvider provider = (BrokerProvider)other;	
	    	 result = getAlias().equals(provider.getAlias());
	    }
	    
	    return result;
	}
	
	@Override
	public int hashCode(){
	    return this.getAlias().hashCode();
	}
}
