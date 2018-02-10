package com.zandero.rest.example.rest;

import com.github.mustachejava.Mustache;
import com.zandero.rest.writer.NotFoundResponseWriter;
import com.zandero.template.MustacheUtils;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import com.zandero.template.*;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class NotFoundHandler extends NotFoundResponseWriter {

	@Override
	public void write(HttpServerRequest request, HttpServerResponse response) {

		response.headers().clear();
		response.headers().add("Content-Type", "text/html;charset=UTF-8");

		response.end(getNotFoundHtml(request.path()));
	}

	private String getNotFoundHtml(String path) {

		Map<String, Object> data = new HashMap<>();
		data.put("path", path);

		return BaseTemplate.direct("/templates/base.html", "/templates/404.html", data);
	}
}
