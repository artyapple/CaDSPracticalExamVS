package cads.impl.app.client;

import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.cads.ev3.gui.swing.CaDSRobotGUISwing;

import cads.impl.FactoryConfig;
import cads.impl.app.client.gui.ConsumerGuiController;
import cads.impl.app.client.gui.GripperMoveGuiController;
import cads.impl.app.client.gui.HorizontalMoveGuiController;
import cads.impl.app.client.gui.RobotManager;
import cads.impl.app.client.gui.VerticalMoveGuiController;
import cads.impl.factory.Factory;
import cads.impl.rpc.configuration.ProvidersConfiguration;
import cads.impl.rpc.consumer.ConsumerControllingServiceIGripperMotor;
import cads.impl.rpc.consumer.ConsumerControllingServiceIHorizontalMotor;
import cads.impl.rpc.consumer.ConsumerControllingServiceIVertikalMotor;
import cads.impl.rpc.consumer.ServicesConfigurationProvider;
import cads.impl.rpc.data.Provider;
import cads.impl.rpc.data.Service;

public class ClientApplication {

	private boolean isMock = false;
	private String brokerHost = "127.0.0.1";
	private int brokerPort = 4001;

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
		new ClientApplication().startup(args);
	}

	public void startup(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {

		FactoryConfig.registerTypesForClient();
		handleProgramArguments(args);

		ProvidersConfiguration providersConfiguration = new ServicesConfigurationProvider()
				.getProviderConfiguration(isMock, brokerHost, brokerPort);

		configurateClient(brokerHost, providersConfiguration);
	}

	private void handleProgramArguments(String[] args) {
		Options options = new Options();
		OptionBuilder.withArgName("simulation or robot").hasArg();
		Option port = OptionBuilder.withLongOpt("port").withDescription("Broker port").create("p");
		options.addOption(port);
		Option ip = OptionBuilder.withLongOpt("ip").withDescription("Broker IP").create("ip");
		options.addOption(ip);
		Option mock = OptionBuilder.withLongOpt("mock").withDescription("Broker mock").create("m");
		options.addOption(mock);

		CommandLineParser parser = new BasicParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		
		try {
			cmd = parser.parse(options, args);
			isMock = cmd.hasOption("mock");	
			brokerHost= cmd.getOptionValue("ip", brokerHost);
			brokerPort= Integer.parseInt(cmd.getOptionValue("port", "4001"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			formatter.printHelp("consumer", options);
			System.exit(1);
            return;
		}

	}

	private void configurateClient(String brokerIp, ProvidersConfiguration providersConfiguration)
			throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {

		Factory factory = Factory.current();

		// default provider for GUI status listener
		if (providersConfiguration.getProviders().size() > 0) {
			RobotManager manager = factory.getInstance(RobotManager.class);
			manager.setCurrent(providersConfiguration.getProviders().get(0).getAlias());
		}

		// create handlers for all exiting providers
		for (Provider provider : providersConfiguration.getProviders()) {
			for (Service service : provider.getServices()) {
				Object controllingService = null;
				switch (service.getServiceName()) {
				case "IGripperMotor":
					controllingService = new ConsumerControllingServiceIGripperMotor(service.getPort(), brokerIp);
					break;
				case "IHorizontalMotor":
					controllingService = new ConsumerControllingServiceIHorizontalMotor(service.getPort(), brokerIp);
					break;
				case "IVertikalMotor":
					controllingService = new ConsumerControllingServiceIVertikalMotor(service.getPort(), brokerIp);
					break;
				default:
					System.out.println("Discoveried service " + service.getServiceName() + " is not supported");
					break;
				}
				if (controllingService != null) {
					factory.registerInstance(controllingService, service.getServiceName() + provider.getAlias());
				}
			}
		}

		// initialize client GUI
		GripperMoveGuiController grabController = new GripperMoveGuiController();
		VerticalMoveGuiController verticalMovingController = new VerticalMoveGuiController();
		HorizontalMoveGuiController horizontalMovingController = new HorizontalMoveGuiController();
		ConsumerGuiController consumerGui = new ConsumerGuiController();
		CaDSRobotGUISwing gui = new CaDSRobotGUISwing(consumerGui, grabController, verticalMovingController,
				horizontalMovingController, null);
		factory.registerInstance(gui, CaDSRobotGUISwing.class);

		// TODO maybe later, not prio 1
		// ConsumerStatusGuiController statusGuiController = new
		// ConsumerStatusGuiController();
		// new Thread(statusGuiController).start();

		// TODO: reg ister each provider in GUI to be able to switch between
		// them
		for (Provider provider : providersConfiguration.getProviders()) {
			gui.addService(provider.getAlias());
			// TODO: and realize status controller (with receiver) via broker
			// factory.registerType(StatusReceiverBuffer.class);
			// StatusGuiController statusController = new StatusGuiController();
			// new Thread(statusController).start();
		}
	}
}
