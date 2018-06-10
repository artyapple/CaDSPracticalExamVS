package cads.impl.app.broker.data;

import java.net.InetAddress;

import cads.impl.rpc.data.Service;

public class BrokerService {

	private int sendingPort;
	private int receivingPort;
	private InetAddress ipAddress;
	private Service service;
	
	public BrokerService(Service service){
		this.service = service;
	}
	
	public int getSendingPort() {
		return sendingPort;
	}
	
	public void setSendingPort(int sendingPort) {
		this.sendingPort = sendingPort;
	}
	
	public int getReceivingPort() {
		return receivingPort;
	}
	
	public void setReceivingPort(int receivingPort) {
		this.receivingPort = receivingPort;
	}
	
	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}
	
	public void setPortForConsumer(int port){
		service.setPort(port);
	}
		
	@Override
	public boolean equals(Object other) {
		boolean result = false;

	    if ((other instanceof BrokerService)){
	    	BrokerService service = (BrokerService)other;	
	    	 result = ipAddress.equals(service.getIpAddress()) && getService().getServiceName().equals(service.getService().getServiceName());
	    }
	    
	    return result;
	}
	
	@Override
	public int hashCode(){
	    return this.getService().getServiceName().hashCode() + this.getIpAddress().hashCode() + this.service.getPort();
	}
}
