package cads.impl.codegen;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cads.impl.rpc.TemplateFiller;
import cads.impl.rpc.configuration.ConfigurationReader;
import cads.impl.rpc.configuration.ServicesConfiguration;
import cads.impl.rpc.data.Service;
import cads.impl.rpc.data.ServiceFunction;
import cads.impl.rpc.data.ServiceFunctionParameter;

public class CodeGenerator {

	private final String PROVIDER_PACKAGE = "cads.impl.rpc.provider";
	private final String CONSUMER_PACKAGE = "cads.impl.rpc.consumer";

	private final String PROVIDER_SERVICE_TEMPLATE_PATH = "gen/plain/service.java.txt";
	private final String CONSUMER_CONTROLLING_SERVICE_TEMPLATE_PATH = "gen/plain/controllingService.java.txt";

	private final String PROVIDER_SERVICES_CONFIGURATION_PATH = "gen/json/services.json";
	private final String ROOT_PATH = "src/";

	public void generate() {
		generateGuiControllingServices();
		generateRobotServices();
	}

	private void generateGuiControllingServices() {
		ServicesConfiguration servicesConfiguration = readServicesConfiguration(PROVIDER_SERVICES_CONFIGURATION_PATH);
		for (Service service : servicesConfiguration.getServices()) {

			if (!service.getIsGenerateable()) {
				continue;
			}

			Map<String, String> extraTemplateVariables = new HashMap<>();
			extraTemplateVariables.put("methods", generateConsumerMethods(service));

			try {
				generateClass(service, CONSUMER_CONTROLLING_SERVICE_TEMPLATE_PATH,
						CONSUMER_PACKAGE, "ConsumerControllingService", extraTemplateVariables);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void generateRobotServices() {
		ServicesConfiguration servicesConfiguration = readServicesConfiguration(PROVIDER_SERVICES_CONFIGURATION_PATH);
		for (Service service : servicesConfiguration.getServices()) {

			if (!service.getIsGenerateable()) {
				continue;
			}

			Map<String, String> extraTemplateVariables = new HashMap<>();
			extraTemplateVariables.put("functionsHandling", createFunctionsHandlingProviderService(service));
			extraTemplateVariables.put("servicePort", String.valueOf(service.getPort()));

			try {
				generateClass(service, PROVIDER_SERVICE_TEMPLATE_PATH,
						PROVIDER_PACKAGE, "ProviderService", extraTemplateVariables);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private ServicesConfiguration readServicesConfiguration(String configPathStr) {
		Path configPath = Paths.get(configPathStr);
		ServicesConfiguration servicesConfiguration = new ConfigurationReader()
				.read(configPath, ServicesConfiguration.class);
		if (servicesConfiguration == null) {
			throw new IllegalStateException("Has no services configuration");
		}
		return servicesConfiguration;
	}

	private void generateClass(Service service, String pathToTemplate,
			String packageForGeneratedJava,
			String generatedClassNamePrefix, Map<String, String> templateVariables)
			throws IOException, ClassNotFoundException {

		String generatedClassName = generatedClassNamePrefix + service.getServiceName();
		String bufferFactoryKey = "Buffer" + generatedClassName;
		//		Factory.current().registerType(bufferClass, bufferClass, bufferFactoryKey);

		// fill service template with defined parameter
		Path serviceTemplatePath = Paths.get(pathToTemplate);
		String unfilledService = new String(Files.readAllBytes(serviceTemplatePath));
		TemplateFiller templateFiller = new TemplateFiller(unfilledService, "\\$")
				.fillTemplate("servicePackageName", packageForGeneratedJava)
				.fillTemplate("generatedClassName", generatedClassName)
				.fillTemplate("serviceName", service.getServiceName())
				.fillTemplate("bufferFactoryKey", bufferFactoryKey);
		for (Entry<String, String> templateVariable : templateVariables.entrySet()) {
			templateFiller.fillTemplate(templateVariable.getKey(), templateVariable.getValue());
		}
		String filledService = templateFiller.getResult();

		// Save filled template in .java file.
		String generatedFileName = generatedClassName + ".java";

		// Save source in .java file.
		File root = new File(ROOT_PATH);
		String pathForGeneratedJava = packageForGeneratedJava.replaceAll("\\.", "/");
		File sourceFile = new File(root, pathForGeneratedJava + "/" + generatedFileName);
		sourceFile.getParentFile().mkdirs();
		Files.write(sourceFile.toPath(), filledService.getBytes(StandardCharsets.UTF_8));
	}

	private String createFunctionsHandlingProviderService(Service service) {
		String functionsHandling = "";
		for (ServiceFunction serviceFunction : service.getFunctions()) {
			// case
			functionsHandling += "case \"" + serviceFunction.getName() + "\": \n";
			// call method
			functionsHandling += "service." + serviceFunction.getName() + "(\n";
			// ...with parameter
			for (ServiceFunctionParameter serviceFunctionParameter : serviceFunction.getParameters()) {
				// read parameter value from received message
				functionsHandling += "message." + serviceFunctionParameter.getType() + "ValueFor(" + "\""
						+ serviceFunctionParameter.getName() + "\"),";
			}
			// ...remove last comma
			if (serviceFunction.getParameters().size() > 0) {
				functionsHandling = functionsHandling.substring(0, functionsHandling.length() - 1);
			}
			// close case block
			functionsHandling += ");\nbreak;";
		}

		return functionsHandling;
	}

	private String generateConsumerMethods(Service service) {
		String methods = "";
		for (ServiceFunction serviceFunction : service.getFunctions()) {
			// signature
			methods += "@Override\n" + "public " + serviceFunction.getReturnType() + " " + serviceFunction.getName()
					+ "(";

			// ... with parameter
			for (ServiceFunctionParameter funktionParameter : serviceFunction.getParameters()) {
				methods += funktionParameter.getType() + " " + funktionParameter.getName() + ", ";
			}
			methods = methods.substring(0, methods.length() - 2); // remove ", " after last parameter

			// put new message in buffer
			methods += ") {\n" + "    putMessageToBuffer(new ServiceMessage("
					+ generateNewConsumerServiceFunction(serviceFunction) + "));\n" + "}\n";
		}

		return methods;
	}

	private String generateNewConsumerServiceFunction(ServiceFunction serviceFunction) {
		String funktionParameters = "Arrays.asList(";
		for (ServiceFunctionParameter funktionParameter : serviceFunction.getParameters()) {
			funktionParameters += generateNewConsumerFunctionParameter(funktionParameter) + ", ";
		}
		funktionParameters = funktionParameters.substring(0, funktionParameters.length() - 2); // remove "," after last argument
		funktionParameters += ")";

		return "new ServiceFunction(" + "\"" + serviceFunction.getName() + "\", " + funktionParameters + ", " + "\""
				+ serviceFunction.getReturnType() + "\")";
	}

	private String generateNewConsumerFunctionParameter(ServiceFunctionParameter funktionParameter) {
		return "new ServiceFunctionParameter(" + "\"" + funktionParameter.getType() + "\", " + "\""
				+ funktionParameter.getName() + "\", " + "String.valueOf(" + funktionParameter.getName() + "))";
	}
}
