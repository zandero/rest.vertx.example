package com.zandero.rest.example;

import com.google.inject.Module;
import com.zandero.rest.RestBuilder;
import com.zandero.rest.example.injection.GuiceInjectionProvider;
import com.zandero.rest.example.rest.EchoRest;
import com.zandero.rest.example.rest.NotFoundHandler;
import com.zandero.rest.example.rest.RestErrorHandler;
import com.zandero.rest.example.rest.RestNotFoundHandler;
import com.zandero.rest.example.injection.ServiceModule;
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
 *
 */
public class ServerVerticle extends AbstractVerticle {

	public static final String API_ROOT = "/api";

	private static final Logger log = LoggerFactory.getLogger(ServerVerticle.class);

	private final ServerSettings settings;

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

		// Inject services
		Router router = new RestBuilder(vertx)
			                .injectWith(new GuiceInjectionProvider(getModules()))
			                .enableCors("*", false, -1, allowedHeaders,
			                            HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
			                .register(EchoRest.class)

			                .notFound(API_ROOT, RestNotFoundHandler.class) // rest not found info
			                .notFound(NotFoundHandler.class) // last resort 404 page
			                .errorHandler(RestErrorHandler.class)
			                .build();


		String logFile = settings.getLog();
		if (logFile != null) {
			LogUtils.setConfig(logFile);
		} else {
			LogUtils.setConfig(ResourceUtils.getResourceAbsolutePath("/logback.xml"));
		}

		int port = settings.getPort();
		log.info("Listening on port: " + port);

		vertx.createHttpServer()
		     .requestHandler(router::accept)
		     .listen(port);
	}

	private Module[] getModules() {
		return new Module[]{
			new ServiceModule()
		};
	}
}
