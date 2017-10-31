package eukaryote.elation;

import static spark.Spark.init;
import static spark.Spark.port;
import static spark.Spark.webSocket;

import java.io.File;
import java.io.IOException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;

import eukaryote.elation.db.PersistenceProvider;
import eukaryote.elation.networking.ConnectionPool;
import eukaryote.elation.networking.IngressSocket;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppContext {
	@Getter
	PersistenceProvider persistenceProvider;

	@Getter
	Configuration config;
	
	@Getter
	ConnectionPool connections;

	public AppContext(PersistenceProvider pp) {
		this.persistenceProvider = pp;

		loadConfig();

		int port = config.getInt("port");
		log.info("Starting on port {}", port);
		
		connections = new ConnectionPool(this, port);
	}

	private void loadConfig() {
		Configurations configs = new Configurations();
		File configfile = new File("elation.cfg");

		if (!configfile.exists())
			try {
				FileUtils.copyURLToFile(ClassLoader.getSystemResource("default.cfg"), configfile);
			} catch (IOException e) {
				log.error("Something went wrong whilst trying to make default configuration file!", e);

				throw new Error();
			}

		try {
			config = configs.properties(configfile);
		} catch (ConfigurationException e) {
			log.error("Something went wrong loading the configuration file!", e);

			throw new Error();
		}
	}
}
