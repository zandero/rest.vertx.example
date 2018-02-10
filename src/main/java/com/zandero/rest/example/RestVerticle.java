package com.zandero.rest.example;

import com.zandero.rest.RestBuilder;
import com.zandero.rest.example.rest.EchoRest;
import com.zandero.rest.example.rest.NotFoundHandler;
import com.zandero.rest.example.rest.RestNotFoundHandler;
import com.zandero.rest.example.rest.SlowRest;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class RestVerticle extends AbstractVerticle {

	private static final Logger log = LoggerFactory.getLogger(RestVerticle.class);

	private final int port;

	public RestVerticle(int apiPort) {

		port = apiPort;
	}

	@Override
	public void start() {

		Router router = new RestBuilder(vertx)
			                .register(EchoRest.class) // simple echo rest
			                .register(SlowRest.class) // slow responding rest
			                .notFound("/rest", RestNotFoundHandler.class) // rest not found info
			                .notFound(NotFoundHandler.class) // last resort 404 page
			                .build();


		log.info("Listening on port: " + port);

		vertx.createHttpServer()
		     .requestHandler(router::accept)
		     .listen(port);
	}
}
