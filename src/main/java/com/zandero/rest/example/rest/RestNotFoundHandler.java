package com.zandero.rest.example.rest;

import com.zandero.rest.example.rest.dto.ErrorJson;
import com.zandero.rest.writer.NotFoundResponseWriter;
import com.zandero.utils.extra.JsonUtils;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

/**
 *
 */
public class RestNotFoundHandler extends NotFoundResponseWriter {

	@Override
	public void write(HttpServerRequest request, HttpServerResponse response) {

		response.headers().clear();
		response.headers().add("Content-Type", "application/json;charset=UTF-8");

		response.end(getNotFoundHtml(request.path()));
	}

	private String getNotFoundHtml(String path) {

		ErrorJson json = new ErrorJson(404, "Resource: '" + path + "', not found.");
		return JsonUtils.toJson(json);
	}
}
