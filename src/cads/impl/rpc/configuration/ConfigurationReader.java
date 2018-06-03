package cads.impl.rpc.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigurationReader {

	@SuppressWarnings("unchecked")
	public <T> T read(Path configPath, Class<? extends Object> clazz) {
		T config = null;
		if (Files.exists(configPath)) {
			try {
				String toDeserialize = new String(Files.readAllBytes(configPath));
				ObjectMapper mapper = new ObjectMapper();
				config = (T) mapper.readValue(toDeserialize, clazz);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return (T) config;
	}

	@SuppressWarnings("unchecked")
	public <T> T readStrConfig(String sconfig, Class<? extends Object> clazz) {
		T config = null;
		try {
			String toDeserialize = sconfig;
			ObjectMapper mapper = new ObjectMapper();
			config = (T) mapper.readValue(toDeserialize, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (T) config;
	}
}
