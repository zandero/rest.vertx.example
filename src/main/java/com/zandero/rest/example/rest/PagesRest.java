package com.zandero.rest.example.rest;

import com.zandero.rest.annotation.ResponseWriter;
import com.zandero.rest.annotation.RouteOrder;
import com.zandero.rest.example.rest.writers.FileResourceWriter;
import com.zandero.utils.StringUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Handles all static pages ... simply by loading resources ...
 */
public class PagesRest {

	@RouteOrder(1000) // last
	@GET
	@Path(".*")
	//@Path("/{path:.*}") // take any path
	@ResponseWriter(FileResourceWriter.class)
	public String serveDocFile(@PathParam("path") String path) {

		if (StringUtils.isNullOrEmptyTrimmed(path)) {
			path = "index.html"; // go to index.html
		}

		return "html/" + path;
	}
}
