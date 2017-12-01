package com.zandero.rest.example.rest;

import com.zandero.rest.RestBuilder;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.Router;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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

		client.getNow("/rest/echo", response -> {

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

		client.getNow("/rest/tralala", response -> {

			context.assertEquals(404, response.statusCode());

			response.handler(body -> {
				context.assertEquals("{\"message\":\"Resource: '/rest/tralala', not found.\",\"code\":404}", body.toString());
				async.complete();
			});
		});
	}
}
