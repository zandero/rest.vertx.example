package com.zandero.rest.example;

/**
 *
 */

import com.zandero.cmd.CommandBuilder;
import com.zandero.cmd.CommandLineException;
import com.zandero.cmd.CommandLineParser;
import com.zandero.cmd.option.CommandOption;
import com.zandero.cmd.option.IntOption;
import com.zandero.settings.Settings;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jetty set up with Guice and RestEasy
 */
public class Server extends AbstractVerticle {

	private static final String PORT_SETTING = "port";
	private static final String POOL_SIZE = "pool";

	private static final Logger log = LoggerFactory.getLogger(Server.class);

	private static Settings settings;

	public static void main(String[] args) {

		CommandOption portOption = new IntOption("p")
			                           .longCommand(PORT_SETTING)
			                           .setting(PORT_SETTING)
			                           .defaultsTo(4444);

		CommandOption poolSizeOption = new IntOption("s")
			                               .longCommand(POOL_SIZE)
			                               .setting(POOL_SIZE)
			                               .defaultsTo(10);

		CommandBuilder builder = new CommandBuilder();
		builder.add(portOption);
		builder.add(poolSizeOption);

		CommandLineParser parser = new CommandLineParser(builder);

		try {
			settings = parser.parse(args);

			// read settings
			int port = settings.getInt(PORT_SETTING);
			int poolSize = settings.getInt(POOL_SIZE);

			// deploy verticle
			Vertx vertx = Vertx.vertx();

			DeploymentOptions options = new DeploymentOptions();
			options.setWorkerPoolSize(poolSize);

			vertx.deployVerticle(new RestVerticle(port), options);
		}
		catch (CommandLineException e) {
			log.error("Failed to get settings: ", e);
			// TODO: show Help
		}
	}
}

