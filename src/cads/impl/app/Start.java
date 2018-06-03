//package cads.impl.app;
//
//import java.io.IOException;
//import java.net.SocketException;
//import java.net.UnknownHostException;
//import java.util.Arrays;
//
//import org.apache.commons.cli.BasicParser;
//import org.apache.commons.cli.CommandLine;
//import org.apache.commons.cli.CommandLineParser;
//import org.apache.commons.cli.HelpFormatter;
//import org.apache.commons.cli.Option;
//import org.apache.commons.cli.OptionBuilder;
//import org.apache.commons.cli.Options;
//import org.apache.commons.cli.ParseException;
//import org.cads.ev3.middleware.CaDSEV3RobotType;
//
//import cads.impl.app.client.ClientApplication;
//import cads.impl.app.server.ServerApplication;
//
//public class Start {
//	
//
//	public static void main(String[] args) {
//		CommandLineParser parser = new BasicParser();
//		Options options = new Options();
//
//		defineOptions(options);
//		commandHandling(args, options, parser);
//		
//	}
//
//	@SuppressWarnings("static-access")
//	private static void defineOptions(Options options) {
//
//		options.addOption("c", "client", false, "Start client");
//
//		Option server = OptionBuilder.withArgName("simulation or robot")
//				.hasArg()
//				.withLongOpt("server")
//				.withDescription("Start server")
//				.create("s");
//		options.addOption(server);
//
//		options.addOption("p", "proxy", false, "Start proxy"); // TODO proxy not yet implemented
//
//		Option serverHost = OptionBuilder.withArgName("hostnames/IPs").hasArgs().withLongOpt("serverHost")
//				.withDescription("Set server hostnames or IPs").create("sh");
//		options.addOption(serverHost);
//
//		Option clientHost = OptionBuilder.withArgName("hostname/ip").hasArg().withLongOpt("clientHost")
//				.withDescription("Set client or proxy hostname for server").create("ch");
//		options.addOption(clientHost);
//
//		Option gripperPort = OptionBuilder.withArgName("port")
//				.hasArgs()
//				.withDescription("Set port")
//				.withLongOpt("gripperPort")
//				.create("gp");
//		options.addOption(gripperPort);
//		
//		Option verticalPort = OptionBuilder.withArgName("port")
//				.hasArgs()
//				.withDescription("Set port")
//				.withLongOpt("verticalPort")
//				.create("vp");
//		options.addOption(verticalPort);
//		
//		Option horizontalPort = OptionBuilder.withArgName("port")
//				.hasArgs()
//				.withDescription("Set port")
//				.withLongOpt("horizontalPort")
//				.create("hp");
//		options.addOption(horizontalPort);
//		
//		Option watchdogDestPort = OptionBuilder.withArgName("port")
//				.hasArgs()
//				.withDescription("Set port")
//				.withLongOpt("watchdogDestPort")
//				.create("wd");
//		options.addOption(watchdogDestPort);
//		
//		Option watchdogLocalPort = OptionBuilder.withArgName("port")
//				.hasArgs()
//				.withDescription("Set port")
//				.withLongOpt("watchdogLocalPort")
//				.create("wl");
//		options.addOption(watchdogLocalPort);
//
//		options.addOption("h", "help", false, "Shows this help");
//	}
//
//	private static void commandHandling(String[] args, Options options, CommandLineParser parser) {
//		try {
//			CommandLine commandLine = parser.parse(options, args);
//
//			// some test handling
//			
////			System.out.println(commandLine.getOptionValue("c"));
////			System.out.println(commandLine.getOptionValue("s"));
////			System.out.println(commandLine.getOptionValue("sh"));
//			/*
//			for (String s : commandLine.getOptionValues("serverHost")) {
//				System.out.println(s);
//			}
//			System.out.println("serverHost Arguments:" + commandLine.getOptionValues("serverHost").length);
//			*/
//
//			//if (commandLine.hasOption("h")) { // just do it at start
//				HelpFormatter formatter = new HelpFormatter();
//
//				formatter.printHelp("Start -option value\n" 
//						+ "examples:\n"
//						+ "Start -s simulation --clientHost localhost\n" 
//						+ "Start -s robot --clientHost 10.0.1.1\n"
//						+ "Start -c --serverHost 10.0.1.60 -gp 1001 -hp 1002 -vp 1003\n", options);
//			//}
//			
//			if (commandLine.hasOption("client")) {
//				// TODO start ClientApplication with parameters
//				ClientApplication clientApp = new ClientApplication();
//				if (commandLine.hasOption("serverHost")) {
//					String serverHost = commandLine.getOptionValue("serverHost");
//					clientApp.setServerHost(serverHost);
//				}
//				if (commandLine.hasOption("gripperPort")) {
//					int[] gripperPorts = stringToIntArray(commandLine.getOptionValues("gp"));
//					clientApp.setGripperPort(gripperPorts[0]);
//				}
//				if (commandLine.hasOption("horizontalPort")) {
//					int[] horizontalPorts = stringToIntArray(commandLine.getOptionValues("hp"));
//					clientApp.setHorizontalPort(horizontalPorts[0]);
//				}
//				if (commandLine.hasOption("verticalPort")) {
//					int[] verticalPorts = stringToIntArray(commandLine.getOptionValues("vp"));
//					clientApp.setVerticalPort(verticalPorts[0]);
//				}
//				if (commandLine.hasOption("serverHost")) {
//					// one host for now
//					String serverHost = commandLine.getOptionValue("serverHost");
//					clientApp.setServerHost(serverHost);
//				}
//				if (commandLine.hasOption("watchdogLocalPort") && commandLine.hasOption("watchdogDestPort")) {
//					clientApp.setWatchdogDestPort(commandLine.getOptionValue("wd"));
//					clientApp.setWatchdogLocalPort(commandLine.getOptionValue("wl"));
//				}
//				try {
//					clientApp.nomain();
//				} catch (InstantiationException | IllegalAccessException | SocketException | UnknownHostException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			if (commandLine.hasOption("server")) {
//				ServerApplication serverApp = new ServerApplication();
//				if (commandLine.hasOption("clientHost")) {
//					String clientHost = commandLine.getOptionValue("clientHost");
//					serverApp.setClientHost(clientHost);
//				}
//				if (commandLine.hasOption("gripperPort")) {
//					int[] gripperPorts = stringToIntArray(commandLine.getOptionValues("gp"));
//					serverApp.setGripperPort(gripperPorts[0]);
//				}
//				if (commandLine.hasOption("horizontalPort")) {
//					int[] horizontalPorts = stringToIntArray(commandLine.getOptionValues("hp"));
//					serverApp.setHorizontalPort(horizontalPorts[0]);
//				}
//				if (commandLine.hasOption("verticalPort")) {
//					int[] verticalPorts = stringToIntArray(commandLine.getOptionValues("vp"));
//					serverApp.setVerticalPort(verticalPorts[0]);
//				}
//				if (commandLine.getOptionValue("server").equals("robot")) {
//					serverApp.setRobotType(CaDSEV3RobotType.REAL);
//				} else if (commandLine.getOptionValue("server").equals("simulation")) {
//					serverApp.setRobotType(CaDSEV3RobotType.SIMULATION);
//				}
//				try {
//					serverApp.nomain();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if (commandLine.hasOption("watchdogLocalPort") && commandLine.hasOption("watchdogDestPort")) {
//					serverApp.setWatchdogDestPort(commandLine.getOptionValue("wd"));
//					serverApp.setWatchdogLocalPort(commandLine.getOptionValue("wl"));
//				}
//			}
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static int[] stringToIntArray(String[] optionValues) {
//		int[] newIntArray = new int[optionValues.length];
//		for (int i = 0; i < newIntArray.length; i++) {
//			newIntArray[i] = Integer.parseInt(optionValues[i]);
//		}
//		return newIntArray;
//	}
//
//}