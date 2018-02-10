package com.zandero.rest.example.rest;

import com.zandero.rest.annotation.ResponseWriter;
import com.zandero.rest.example.service.SlowService;
import com.zandero.rest.writer.GenericResponseWriter;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

/**
 * Slow working rest for simulation purposes only
 */
@Path("/rest/slow")
public class SlowRest {

	private SlowService slowService;

	public SlowRest() {

		slowService = new SlowService();
	}

	@GET
	@Path("down/{time}")
	public int slowDown(@PathParam("time") int time) {

		slowService.wait(time);
		return time;
	}

	@GET
	@Path("async/{time}")
	@ResponseWriter(GenericResponseWriter.class)
	public Future<String> async(@Context Vertx vertx, @PathParam("time") int time) {

		Future<String> future = Future.future();
		slowService.async(vertx, future, time);
		return future;
	}
}
