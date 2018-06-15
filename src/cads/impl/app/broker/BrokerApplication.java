package cads.impl.app.broker;

import java.net.SocketException;
import java.net.UnknownHostException;

import cads.impl.FactoryConfig;
import cads.impl.app.broker.lookup.LookupService;
import cads.impl.app.broker.register.RegisterService;

public class BrokerApplication {

	private String brokerHost = "0.0.0.0";
	// private int brokerPort = 4001;

	private final static int REGISTER_PORT = 10001;
	private final static int LOOKUP_PORT = 10000;

	public static void main(String[] args) {
		try {
			new BrokerApplication().startup(args);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void startup(String[] args) throws InstantiationException, IllegalAccessException {
		try {
			handleProgramArguments(args);
			FactoryConfig.registerTypesForBrocker();

			RegisterService registerService = new RegisterService(brokerHost, REGISTER_PORT);
			new Thread(registerService).start();

			LookupService lookupService = new LookupService(brokerHost, LOOKUP_PORT);
			new Thread(lookupService).start();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private void handleProgramArguments(String[] args) {
		if (args.length != 0) {
			String ip = args[0];
			if (ip != null && !ip.isEmpty()){
				brokerHost = ip.replaceAll(" ", "");
			}
		}
		System.out.println("> Broker host: "+brokerHost);
	}
}