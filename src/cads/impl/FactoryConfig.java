package cads.impl;

import cads.impl.app.broker.data.RegisteredProviders;
import cads.impl.app.broker.forward.ForwardingService;
import cads.impl.app.client.gui.RobotManager;
import cads.impl.factory.Factory;
//import cads.impl.mom.communication.buffer.StatusSenderBuffer;
//import cads.impl.shared.MarshallingService;
//import cads.impl.soa.broker.RegisteredProviders;
//import cads.impl.soa.broker.forwarding.ForwardingService;
//import cads.impl.soa.consumer.RobotManager;
//import cads.impl.soa.data.FullStatus;
import cads.impl.mom.MarshallingService;

public class FactoryConfig {

	public static void registerTypes() {
		Factory fac = Factory.current();
		fac.registerType(MarshallingService.class);
	}

	public static void registerTypesForBrocker() {
		registerTypes();

		Factory fac = Factory.current();
		fac.registerType(ForwardingService.class);
		fac.registerType(RegisteredProviders.class);
	}
//
	public static void registerTypesForService() {
		registerTypes();

		Factory fac = Factory.current();
		//fac.registerType(StatusSenderBuffer.class);
		//fac.registerType(FullStatus.class);
	}

	public static void registerTypesForClient() {
		registerTypes();
		Factory fac = Factory.current();
		fac.registerType(RobotManager.class);
	}

}
