package com.zandero.rest.example.rest;

import com.zandero.rest.annotation.ResponseWriter;
import com.zandero.rest.example.service.SlowService;
import com.zandero.rest.writer.GenericResponseWriter;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import static com.zandero.rest.example.ServerVerticle.API_ROOT;

/**
 * Slow working rest for simulation purposes only
 */
@Path(API_ROOT + "/slow")
public class SlowRest {

    private SlowService slowService;

    @Inject
    public SlowRest(SlowService injectedSlow) {

        slowService = injectedSlow;
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
    public Promise<String> async(@Context Vertx vertx, @PathParam("time") int time) {

        Promise<String> future = Promise.promise();
        slowService.async(vertx, future, time);
        return future;
    }
}
