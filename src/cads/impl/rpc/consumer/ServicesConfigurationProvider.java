package cads.impl.rpc.consumer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cads.impl.factory.Factory;
import cads.impl.mom.MarshallingService;
import cads.impl.mom.buffer.Buffer;
import cads.impl.mom.buffer.LookupMessage;
import cads.impl.mom.middleware.ServerMiddleware;
import cads.impl.os.Client;
import cads.impl.os.UDPClient;
import cads.impl.rpc.configuration.ConfigurationReader;
import cads.impl.rpc.configuration.ProvidersConfiguration;

public class ServicesConfigurationProvider {

	private static final String MOCK_PROVIDERS_PATH = "gen/json/providers.mock.json";
	
	private MarshallingService marshalingService;
	
	public ServicesConfigurationProvider() throws InstantiationException, IllegalAccessException{
		this.marshalingService = Factory.current().getInstance(MarshallingService.class);
	}
	
	public ProvidersConfiguration getProviderConfiguration(boolean isMock, String brokerIp, int brokerPort) 
			throws JsonParseException, JsonMappingException, InstantiationException, IllegalAccessException, IOException {
		
		if (isMock) {
			// Read configuration file
			Path configPath = Paths.get(MOCK_PROVIDERS_PATH);
			return new ConfigurationReader()
					.read(configPath, ProvidersConfiguration.class);
		}
		return getProviderConfigurationFromBroker(brokerIp, brokerPort);
	}
	
	private ProvidersConfiguration getProviderConfigurationFromBroker(String brokerIp, int brokerPort) 
			throws JsonParseException, JsonMappingException, IOException, InstantiationException, IllegalAccessException {

		// sender to require providers configuration
		Client sender = new UDPClient(brokerIp, brokerPort);
		DatagramSocket socket = sender.getSocket();

		// receiver to receive providers configuration
		Buffer<String> providersConfigurationBuffer = new Buffer<>(1);
		ServerMiddleware receiver = new ServerMiddleware(providersConfigurationBuffer, socket);
		new Thread(receiver).start();
		
		System.out.println("Consumer send request to brocker");
		String providersConfiguration = null;
		while (providersConfiguration == null) {
			sender.send("Request for providers configuration");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			providersConfiguration = providersConfigurationBuffer.getLast();
			System.out.println("Consumer Retrieved: " + providersConfiguration);
		}
		
		LookupMessage message =  marshalingService.deSerialize(providersConfiguration, LookupMessage.class);
		ProvidersConfiguration result = new ProvidersConfiguration(message.getProviders());
		
		return result;
	}
}
