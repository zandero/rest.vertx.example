package com.zandero.rest.example;

/**
 *
 */

import com.zandero.cmd.CommandBuilder;
import com.zandero.cmd.CommandLineException;
import com.zandero.cmd.CommandLineParser;
import com.zandero.cmd.option.CommandOption;
import com.zandero.cmd.option.IntOption;
import com.zandero.rest.RestBuilder;
import com.zandero.rest.example.rest.EchoRest;
import com.zandero.rest.example.rest.NotFoundHandler;
import com.zandero.rest.example.rest.RestNotFoundHandler;
import com.zandero.settings.Settings;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Launcher;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jetty set up with Guice and RestEasy
 */
public class Server extends AbstractVerticle {

	private static final String PORT_SETTING = "port";

	private static final Logger log = LoggerFactory.getLogger(Server.class);

	private static Settings settings;

	public static void main(String[] args) throws CommandLineException {

		CommandOption port = new IntOption("p")
			                     .longCommand(PORT_SETTING)
			                     .setting(PORT_SETTING)
			                     .defautlTo(4444);

		CommandBuilder builder = new CommandBuilder();
		builder.add(port);

		CommandLineParser parser = new CommandLineParser(builder);
		settings = parser.parse(args);

		Launcher.executeCommand("run", Server.class.getName());
	}

	@Override
	public void start() {

		Router router = new RestBuilder(vertx)
			                .register(EchoRest.class) // simple echo rest
			                .notFound("/rest", RestNotFoundHandler.class) // rest not found info
							.notFound(NotFoundHandler.class) // last resort 404 page
			                .build();

		Handler<RoutingContext> noMatchHandler = context -> {
			context.response().end("Hello world!");
		};
		router.route().last().handler(noMatchHandler);

		int port = settings.getInt(PORT_SETTING);
		log.info("Listening on port: " + port);

		vertx.createHttpServer()
		     .requestHandler(router::accept)
		     .listen(port);
	}
}

