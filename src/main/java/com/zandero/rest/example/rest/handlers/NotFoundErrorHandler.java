package com.zandero.rest.example.rest.handlers;

import com.zandero.rest.exception.ExceptionHandler;
import com.zandero.template.BaseTemplate;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class NotFoundErrorHandler implements ExceptionHandler<FileNotFoundException> {

	@Override
	public void write(FileNotFoundException result, HttpServerRequest request, HttpServerResponse response) throws Throwable {

		response.end(getNotFoundHtml(request.path()));
	}

	private String getNotFoundHtml(String path) {

		Map<String, Object> data = new HashMap<>();
		data.put("path", path);

		return BaseTemplate.direct("/templates/base.html", "/templates/404.html", data);
	}
}
