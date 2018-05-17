package cads.impl.app;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Start {

	@SuppressWarnings("static-access")
	private static void defineOptions(Options options) {

		options.addOption("c", "client", false, "Start client");

		Option server = OptionBuilder.withArgName("simulation or robot").hasArg().isRequired().withLongOpt("server")
				.withDescription("Start server").create("s");
		options.addOption(server);

		options.addOption("p", "proxy", false, "Start proxy"); // TODO proxy not yet implemented

		Option serverHost = OptionBuilder.withArgName("hostnames/IPs").hasArgs().withLongOpt("serverHost")
				.withDescription("Set server hostnames or IPs").create("sh");
		options.addOption(serverHost);

		Option clientHost = OptionBuilder.withArgName("hostname/ip").hasArg().withLongOpt("clientHost")
				.withDescription("Set client or proxy hostname for server").create("ch");
		options.addOption(clientHost);

		Option gripperPort = OptionBuilder.withArgName("port")
				.hasArg()
				.withDescription("Set port")
				.create("gp");
		options.addOption(gripperPort);
		
		Option verticalPort = OptionBuilder.withArgName("port")
				.hasArg()
				.withDescription("Set port")
				.create("vp");
		options.addOption(verticalPort);
		
		Option horizontalPort = OptionBuilder.withArgName("port")
				.hasArg()
				.withDescription("Set port")
				.create("hp");
		options.addOption(horizontalPort);

		options.addOption("h", "help", false, "Shows this help");
	}

	private static void commandHandling(String[] args, Options options, CommandLineParser parser) {
		try {
			CommandLine commandLine = parser.parse(options, args);

			// some test handling
			/*
			System.out.println(commandLine.getOptionValue("c"));
			System.out.println(commandLine.getOptionValue("s"));
			System.out.println(commandLine.getOptionValue("sh"));
			for (String s : commandLine.getOptionValues("serverHost")) {
				System.out.println(s);
			}
			System.out.println("serverHost Arguments:" + commandLine.getOptionValues("serverHost").length);
			*/

			if (commandLine.hasOption("h")) {
				HelpFormatter formatter = new HelpFormatter();

				formatter.printHelp("Start -option value\n" 
						+ "examples:\n"
						+ "Start -s simulation --clientHost localhost\n" 
						+ "Start -s robot --clientHost 10.0.1.1\n"
						+ "Start -c --serverHost 10.0.1.60 -gp 1001 -hp 1002 -vp 1003\n", options);
			}
			
			if (commandLine.hasOption("client")) {
				if (commandLine.hasOption("serverHost")) {
					String serverHost = commandLine.getOptionValue("serverHost");
				}
				if (commandLine.hasOption("gripperPort")) {
					int gripperPort = Integer.getInteger(commandLine.getOptionValue("gripperPort"));
				}
				if (commandLine.hasOption("horizontalPort")) {
					int horizontalPort = Integer.getInteger(commandLine.getOptionValue("horizontalPort"));
				}
				if (commandLine.hasOption("verticalPort")) {
					int verticalPort = Integer.getInteger(commandLine.getOptionValue("verticalPort"));
				}
				if (commandLine.hasOption("serverHost")) {
					// one host for now
					String serverHost = commandLine.getOptionValue("serverHost");
				}
				// TODO start ClientApplication with parameters
			}
			
			if (commandLine.hasOption("server")) {
				if (commandLine.hasOption("clientHost")) {
					String clientHost = commandLine.getOptionValue("clientHost");
				}
				if (commandLine.getOptionValue("server").equals("simulation")) {
					// TODO start ServerApplication with simulation mode
				} else {
					// TODO start ServerApplication with robot (default?) mode
				}
				// setup other services
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CommandLineParser parser = new BasicParser();
		Options options = new Options();

		defineOptions(options);
		commandHandling(args, options, parser);
		
	}
}