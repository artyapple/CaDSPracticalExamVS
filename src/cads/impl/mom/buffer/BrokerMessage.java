package cads.impl.mom.buffer;

import java.net.InetAddress;

import org.json.simple.JSONObject;

import cads.impl.mom.IDeSerializable;

public class BrokerMessage implements IDeSerializable<BrokerMessage> {
	
	private int sourcePort;
	private InetAddress sourceAddress;
	private String message;
	
	public BrokerMessage(){
	}
	
	public BrokerMessage(InetAddress sourceAddress, int sourcePort) {
		this.sourceAddress = sourceAddress;
		this.sourcePort = sourcePort;
	}
	
	@Override
	public BrokerMessage deSerialize(JSONObject jsonObject) throws Exception {
		sourcePort = ((Long) jsonObject.get("sourcePort")).intValue();
		sourceAddress = InetAddress.getByName((String) jsonObject.get("sourceAddress"));
		message = (String)jsonObject.get("message");

		return this;
	}
	
	public InetAddress getSourceAddress(){
		return sourceAddress;
	}
	
	
	public void setSourceAddress(InetAddress sourceAddress){
		this.sourceAddress = sourceAddress;
	}
	
	public int getSourcePort(){
		return sourcePort;
	}
	
	public void setSourcePort(int sourcePort){
		this.sourcePort = sourcePort;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
