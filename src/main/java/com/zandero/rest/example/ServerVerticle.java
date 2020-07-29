package com.zandero.rest.example;

import com.google.inject.Module;
import com.zandero.rest.RestBuilder;
import com.zandero.rest.example.injection.GuiceInjectionProvider;
import com.zandero.rest.example.injection.ServiceModule;
import com.zandero.rest.example.rest.EchoRest;
import com.zandero.rest.example.rest.PagesRest;
import com.zandero.rest.example.rest.SlowRest;
import com.zandero.rest.example.rest.handlers.NotFoundErrorHandler;
import com.zandero.rest.example.rest.handlers.RestErrorHandler;
import com.zandero.rest.example.rest.handlers.RestNotFoundHandler;
import com.zandero.rest.example.utils.LogUtils;
import com.zandero.utils.ResourceUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * This is a basic server set up to get REST.vertx going ...
 */
public class ServerVerticle extends AbstractVerticle {

	private static final Logger log = LoggerFactory.getLogger(ServerVerticle.class);

	/**
	 * Root URL for REST endpoints
	 */
	public static final String API_ROOT = "/api";

	/**
	 * Command line setting holder
	 */
	private final ServerSettings settings;

	/**
	 * @param serverSettings startup settings
	 */
	public ServerVerticle(ServerSettings serverSettings) {
		settings = serverSettings;
	}

	@Override
	public void start() {

		Set<String> allowedHeaders = new HashSet<>();
		allowedHeaders.add("Access-Control-Request-Method");
		allowedHeaders.add("Access-Control-Allow-Credentials");
		allowedHeaders.add("Access-Control-Allow-Origin");
		allowedHeaders.add("Access-Control-Allow-Headers");
		allowedHeaders.add("Content-Type");
		allowedHeaders.add("Origin");

		// Create router ...
		Router router = new RestBuilder(vertx)
			                // set injector
			                .injectWith(new GuiceInjectionProvider(getModules()))

			                // enable CORS calls
			                .enableCors("*", false, -1, allowedHeaders,
			                            HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)

			                // register RESTs
			                .register(EchoRest.class,
			                          SlowRest.class,
			                          PagesRest.class)

			                // handle REST / page not found requests
			                .notFound(API_ROOT, RestNotFoundHandler.class) // rest not found info - all under /api/*

			                // add general purpose REST error handler
			                .errorHandler(NotFoundErrorHandler.class)
			                .errorHandler(RestErrorHandler.class)
			                .build();


		// set logging
		String logFile = settings.getLog();
		if (logFile != null) {
			LogUtils.setConfig(logFile);
		} else {
			LogUtils.setConfig(ResourceUtils.getResourceAbsolutePath("/logback.xml"));
		}

		// use port
		int port = settings.getPort();
		log.info("Listening on port: " + port + " - vert.x thread pool size: " + settings.getPoolSize());

		// start up server
		vertx.createHttpServer()
		     .requestHandler(router)
		     .listen(port);
	}

	// provide injected services ...
	private Module[] getModules() {
		return new Module[]{
			new ServiceModule()
		};
	}
}
