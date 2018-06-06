package com.zandero.rest.example.rest;

import com.zandero.rest.RestBuilder;
import com.zandero.rest.example.rest.handlers.RestNotFoundHandler;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.Router;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.zandero.rest.example.ServerVerticle.API_ROOT;

/**
 *
 */
@RunWith(VertxUnitRunner.class)
public class EchoRestTest extends BaseTest {

	@Before
	public void start(TestContext context) {

		super.before(context);

		Router router = new RestBuilder(vertx)
			                .register(EchoRest.class) // /api endpoint
			                .notFound(RestNotFoundHandler.class) // rest not found (last resort)
			                .build();

		vertx.createHttpServer()
		     .requestHandler(router::accept)
		     .listen(PORT);
	}

	@Test
	public void testEcho(TestContext context) {

		// call and check response
		final Async async = context.async();

		client.getNow(API_ROOT + "/echo", response -> {

			context.assertEquals(200, response.statusCode());

			response.handler(body -> {
				context.assertEquals("\"echo\"", body.toString());
				async.complete();
			});
		});
	}

	@Test
	public void testRestNotFound(TestContext context) {

		// call and check response
		final Async async = context.async();

		client.getNow(API_ROOT + "/tralala", response -> {

			context.assertEquals(404, response.statusCode());

			response.handler(body -> {
				context.assertEquals("{\"message\":\"Resource: '" + API_ROOT + "/tralala', not found.\",\"code\":404}", body.toString());
				async.complete();
			});
		});
	}
}
