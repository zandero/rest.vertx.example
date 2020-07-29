package com.zandero.rest.example.rest;

import com.zandero.rest.RestBuilder;
import com.zandero.rest.example.rest.handlers.RestNotFoundHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.zandero.rest.example.ServerVerticle.API_ROOT;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
@ExtendWith(VertxExtension.class)
class EchoRestTest extends BaseTest {

	@BeforeAll
	static void start() {

		before();

		Router router = new RestBuilder(vertx)
			                .register(EchoRest.class) // /api endpoint
			                .notFound(RestNotFoundHandler.class) // rest not found (last resort)
			                .build();

		vertx.createHttpServer()
		     .requestHandler(router)
		     .listen(PORT);
	}

	@Test
	public void testEcho(VertxTestContext context) {

		client.get(PORT, HOST, API_ROOT + "/echo").as(BodyCodec.string())
			.send(context.succeeding(response -> context.verify(() -> {
				assertEquals(200, response.statusCode());
				assertEquals("\"echo\"", response.body());
				context.completeNow();
			})));
	}

	@Test
	public void testRestNotFound(VertxTestContext context) {

		client.get(PORT, HOST, API_ROOT + "/tralala").as(BodyCodec.string())
			.send(context.succeeding(response -> context.verify(() -> {
				assertEquals(404, response.statusCode());
				assertEquals("{\"message\":\"Resource: '" + API_ROOT + "/tralala', not found.\",\"code\":404}",
							 response.body());
				context.completeNow();
			})));
	}
}
