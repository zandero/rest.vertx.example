package com.zandero.rest.example.rest;

import com.zandero.rest.example.rest.dto.VersionDto;
import com.zandero.rest.example.utils.VersionUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static com.zandero.rest.example.ServerVerticle.API_ROOT;

/**
 *
 */
@Path(API_ROOT)
public class EchoRest {

	@GET
	@Path("/echo")
	@Produces("application/json")
	public String echo() {
		return "echo";
	}

	@GET
	@Path("/version")
	@Produces("application/json")
	public VersionDto version() {

		return VersionUtils.version();
	}
}
