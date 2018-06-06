package com.zandero.rest.example.rest.writers;

import com.zandero.rest.writer.HttpResponseWriter;
import io.vertx.core.file.FileSystem;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import javax.ws.rs.core.Context;
import java.io.FileNotFoundException;

/**
 *
 */
public class FileResourceWriter implements HttpResponseWriter<String> {

	@Context
	RoutingContext context;

	@Override
	public void write(String path, HttpServerRequest request, HttpServerResponse response) throws FileNotFoundException {

		if (!fileExists(context, path)) {
			throw new FileNotFoundException(path);
		}

		response.sendFile(path);
	}

	protected boolean fileExists(RoutingContext context, String file) {
		FileSystem fs = context.vertx().fileSystem();
		return fs.existsBlocking(file);
	}
}
