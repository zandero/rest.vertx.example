package com.zandero.rest.example;

import com.zandero.cmd.CommandLineException;
import com.zandero.rest.example.rest.dto.VersionDto;
import com.zandero.rest.example.utils.VersionUtils;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Server set up ... and start up
 */
public class Server {

	private static final Logger log = LoggerFactory.getLogger(Server.class);

	ServerSettings settings = new ServerSettings();

	public static void main(String[] args) {
		new Server().run(args);
	}

	public void run(String[] args) {

		try {
			// get settings from command line
			settings.parse(args);

			// show version only?
			if (settings.showVersion()) {
				showVersion();
				return;
			}

			// get pool size
			int poolSize = settings.getPoolSize();

			// deploy verticle
			Vertx vertx = Vertx.vertx();

			DeploymentOptions options = new DeploymentOptions();
			options.setWorkerPoolSize(poolSize);

			vertx.deployVerticle(new ServerVerticle(settings), options);
		}
		catch (CommandLineException e) {
			// some command line setting was invalid ... show help
			log.error("Failed to get settings: ", e);

			showVersion();
			showHelp(settings.getHelp());
		}
	}

	private void showHelp(List<String> help) {
		for (String item : help) {
			System.out.println(item);
		}

		System.out.print(System.lineSeparator());
	}

	private void showVersion() {

		VersionDto version = VersionUtils.version();
		System.out.println(String.format("Rest.vertx example API service: %s, %s", version.getVersion(), version.getDate()));
		System.out.print(System.lineSeparator());
	}
}

