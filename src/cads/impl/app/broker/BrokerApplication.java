package cads.impl.app.broker;

import java.net.SocketException;
import java.net.UnknownHostException;

import cads.impl.FactoryConfig;
import cads.impl.app.broker.lookup.LookupService;
import cads.impl.app.broker.register.RegisterService;

public class BrokerApplication {

	//private String brokerHost = "127.0.0.1";
	//private int brokerPort = 4001;
	
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
			FactoryConfig.registerTypesForBrocker();

			RegisterService registerService = new RegisterService(REGISTER_PORT);
			new Thread(registerService).start();

			LookupService lookupService = new LookupService(LOOKUP_PORT);
			new Thread(lookupService).start();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private void handleProgramArguments(String[] args) {
//		Options options = new Options();
//		Option port = OptionBuilder.withLongOpt("port").withDescription("Broker port").create("p");
//		options.addOption(port);
//		Option ip = OptionBuilder.withLongOpt("ip").withDescription("Broker IP").create("ip");
//		options.addOption(ip);
//
//		CommandLineParser parser = new BasicParser();
//		HelpFormatter formatter = new HelpFormatter();
//		CommandLine cmd;
//		
//		try {
//			cmd = parser.parse(options, args);
//			brokerHost= cmd.getOptionValue("ip", brokerHost);
//			//brokerPort= Integer.parseInt(cmd.getOptionValue("port", "4001"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			formatter.printHelp("consumer", options);
//			System.exit(1);
//            return;
//		}

	}
}