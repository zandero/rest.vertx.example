package com.zandero.rest.example.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 */
@Path("/rest")
public class EchoRest {

	@GET
	@Path("/echo")
	@Produces("application/json")
	public String echo() {
		return "echo";
	}
}
