package cads.impl.app.client;

import java.io.IOException;

import org.cads.ev3.gui.swing.CaDSRobotGUISwing;

import cads.impl.FactoryConfig;
import cads.impl.app.client.gui.CaDsGUI;
import cads.impl.app.client.gui.RobotManager;
import cads.impl.app.client.gui.StatusGUIController;
import cads.impl.app.client.rpc.ServicesConfigurationProvider;
import cads.impl.factory.Factory;
import cads.impl.rpc.configuration.ProvidersConfiguration;
import cads.impl.rpc.consumer.ConsumerControllingServiceIGripperMotor;
import cads.impl.rpc.consumer.ConsumerControllingServiceIHorizontalMotor;
import cads.impl.rpc.consumer.ConsumerControllingServiceIVertikalMotor;
import cads.impl.rpc.data.Provider;
import cads.impl.rpc.data.Service;

public class ClientApplication {

	private boolean isMock = false;
	private String brokerHost = "localhost";
	private int brokerPort = 10000; //lookup

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
		new ClientApplication().startup(args);
	}

	public void startup(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {

		FactoryConfig.registerTypesForClient();
		handleProgramArguments(args);
		System.out.println(brokerHost+":"+brokerPort);
		ProvidersConfiguration providersConfiguration = new ServicesConfigurationProvider()
				.getProviderConfiguration(isMock, brokerHost, brokerPort);

		configurateClient(brokerHost, providersConfiguration);
	}

	private void handleProgramArguments(String[] args) {
		String a = args[0];
		a.replaceAll(" ", "");
		if(a.equals("-m")){
			isMock = true;
		} else if (a.contains(":")){
			String[] inf = a.split(":");
			brokerHost = inf[0];
			brokerPort = Integer.parseInt(inf[1]);
		} else {
			System.out.println("param list empty");
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
		/*GripperMoveGuiController grabController = new GripperMoveGuiController();
		VerticalMoveGuiController verticalMovingController = new VerticalMoveGuiController();
		HorizontalMoveGuiController horizontalMovingController = new HorizontalMoveGuiController();
		ConsumerGuiController consumerGui = new ConsumerGuiController();*/
		
		CaDsGUI c = new CaDsGUI();
		CaDSRobotGUISwing gui = new CaDSRobotGUISwing(c, c, c,
				c, null);
		factory.registerInstance(gui, CaDSRobotGUISwing.class);

		// TODO: maybe later, not prio 1
		// ConsumerStatusGuiController statusGuiController = new
		// ConsumerStatusGuiController();
		// new Thread(statusGuiController).start();

		// TODO: register each provider in GUI to be able to switch between
		// them
		for (Provider provider : providersConfiguration.getProviders()) {
			gui.addService(provider.getAlias());
			new Thread(new StatusGUIController(provider.getStatusChannel(), provider.getAlias())).start();
			// TODO: and realize status controller (with receiver) via broker
			// factory.registerType(StatusReceiverBuffer.class);
			// StatusGuiController statusController = new StatusGuiController();
			// new Thread(statusController).start();
		}
	}
}