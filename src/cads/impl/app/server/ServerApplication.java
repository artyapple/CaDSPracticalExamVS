package cads.impl.app.server;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.cads.ev3.middleware.CaDSEV3RobotHAL;
import org.cads.ev3.middleware.CaDSEV3RobotType;

import com.fasterxml.jackson.core.JsonProcessingException;

import cads.impl.FactoryConfig;
import cads.impl.app.server.listener.FeedBackListener;
import cads.impl.app.server.listener.RobotStatusListener;
import cads.impl.app.server.listener.RobotStatusListener.ValueToObserve;
import cads.impl.factory.Factory;
import cads.impl.hal.IGripperMotor;
import cads.impl.hal.IHorizontalMotor;
import cads.impl.hal.IVertikalMotor;
import cads.impl.hal.server.GripperMotor;
import cads.impl.hal.server.HorizontalMotor;
import cads.impl.hal.server.VertikalMotor;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.buffer.Buffer;
import cads.impl.mom.middleware.ClientMiddleware;
import cads.impl.os.UDPClient;
import cads.impl.rpc.configuration.ConfigurationReader;
import cads.impl.rpc.configuration.ServicesConfiguration;
import cads.impl.rpc.data.Provider;
import cads.impl.rpc.data.Service;
import cads.impl.rpc.provider.ProviderServiceIGripperMotor;
import cads.impl.rpc.provider.ProviderServiceIHorizontalMotor;
import cads.impl.rpc.provider.ProviderServiceIVertikalMotor;

public class ServerApplication {

	private String configPathStr = "";
	private boolean isSimulation = false;
	private int brokerPort = 10001;// register
	private String brokerIp = "localhost";

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
		new ServerApplication().startup(args);
	}

	public void startup(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		handleProgramArguments(args);

		FactoryConfig.registerTypesForService();

		initStaticElements();

		ServicesConfiguration servicesConfiguration = readServicesConfiguration();
		startServices(servicesConfiguration);
		sendConfigurationToBroker(servicesConfiguration);
	}

	private void handleProgramArguments(String[] args) {
		// arguments for provider site
		// simulation;localhost:10000;C:\Users\AI\Documents\Studium\SS2018\VS\src\git\CaDSPracticalExamVS\gen\json\services.json
		String a = args[0];
		String[] sarg = a.split(";");
		int i = 0;
		System.out.println(a);
		if (sarg[i].contains("sim")) {
			isSimulation = true;
			i++;
		}
		if (sarg[i].contains(":")) {
			String[] net = sarg[i].replaceAll(" ", "").split(":");
			brokerIp = net[0];
			brokerPort = Integer.parseInt(net[1]);
			i++;
		}
		configPathStr = sarg[i].replaceAll(" ", "");
	}

	private void initStaticElements()
			throws InstantiationException, IllegalAccessException, SocketException, UnknownHostException {
		Factory fac = Factory.current();
		RobotStatusListener robotStatusListener = new RobotStatusListener();
		// initialize robot

		CaDSEV3RobotHAL robot = CaDSEV3RobotHAL.createInstance(
				isSimulation ? CaDSEV3RobotType.SIMULATION : CaDSEV3RobotType.REAL, robotStatusListener,
				new FeedBackListener());
		fac.registerInstance(robot, CaDSEV3RobotHAL.class);
		// initialize robot HAL elements
		GripperMotor gripper = new GripperMotor();
		robotStatusListener.subscribe(ValueToObserve.GRIPPER, gripper);
		fac.registerInstance(gripper, IGripperMotor.class);

		VertikalMotor verticalMotor = new VertikalMotor();
		robotStatusListener.subscribe(ValueToObserve.VERTIKAL, verticalMotor);
		fac.registerInstance(verticalMotor, IVertikalMotor.class);

		HorizontalMotor horizontalMotor = new HorizontalMotor();
		robotStatusListener.subscribe(ValueToObserve.HORIZONTAL, horizontalMotor);
		fac.registerInstance(horizontalMotor, IHorizontalMotor.class);

	}

	private ServicesConfiguration readServicesConfiguration() {
		Path configPath = Paths.get(configPathStr);
		ServicesConfiguration servicesConfiguration = new ConfigurationReader().read(configPath,
				ServicesConfiguration.class);
		if (servicesConfiguration == null) {
			throw new IllegalStateException("Has no services configuration");
		}
		return servicesConfiguration;
	}

	private void startServices(ServicesConfiguration servicesConfiguration) {
		for (Service service : servicesConfiguration.getServices()) {
			Runnable providerService = null;
			int openedServicePort = service.getPort();
			switch (service.getServiceName()) {
			case "IGripperMotor":
				providerService = new ProviderServiceIGripperMotor(openedServicePort);
				break;
			case "IHorizontalMotor":
				providerService = new ProviderServiceIHorizontalMotor(openedServicePort);
				break;
			case "IVertikalMotor":
				providerService = new ProviderServiceIVertikalMotor(openedServicePort);
				break;
			default:
				System.out.println("Service " + service.getServiceName() + " from configuration is not supported");
				continue;
			}
			new Thread(providerService).start();
		}
	}

	private void sendConfigurationToBroker(ServicesConfiguration servicesConfiguration) throws InstantiationException,
			IllegalAccessException, SocketException, UnknownHostException, JsonProcessingException {
		// create initial message for broker
		Provider provider = new Provider();
		provider.setAlias(servicesConfiguration.getAlias());
		provider.setServices(servicesConfiguration.getServices());
		String initialMessage = new MarshallingService().serialize(provider);

		// put the message to buffer to send
		Buffer<String> configurationBuffer = new Buffer<>(1);
		configurationBuffer.add(initialMessage);

		// send initial message to broker (sending will be repeat till the
		// broker confirm the receiving)
		ClientMiddleware sender = new ClientMiddleware(configurationBuffer, new UDPClient(brokerIp, brokerPort));
		new Thread(sender).start();
	}
}
