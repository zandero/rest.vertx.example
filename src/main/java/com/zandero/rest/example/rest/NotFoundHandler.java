package com.zandero.rest.example.rest;

import com.zandero.rest.writer.NotFoundResponseWriter;
import com.zandero.template.BaseTemplate;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all request to any other URL ... and returns 404 HTML page
 */
@Produces("text/html;charset=UTF-8")
public class NotFoundHandler extends NotFoundResponseWriter {

	@Override
	public void write(HttpServerRequest request, HttpServerResponse response) {

		response.end(getNotFoundHtml(request.path()));
	}

	private String getNotFoundHtml(String path) {

		Map<String, Object> data = new HashMap<>();
		data.put("path", path);

		return BaseTemplate.direct("/templates/base.html", "/templates/404.html", data);
	}
}
