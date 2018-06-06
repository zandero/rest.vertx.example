package com.zandero.rest.example.rest.handlers;

import com.zandero.rest.example.rest.dto.ErrorDto;
import com.zandero.rest.writer.NotFoundResponseWriter;
import com.zandero.utils.extra.JsonUtils;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import javax.ws.rs.Produces;

/**
 * Handlers all calls to ROOT_API ("/api") where no handler was found
 */
@Produces("application/json;charset=UTF-8")
public class RestNotFoundHandler extends NotFoundResponseWriter {

	@Override
	public void write(HttpServerRequest request, HttpServerResponse response) {

		response.end(getNotFoundHtml(request.path()));
	}

	private String getNotFoundHtml(String path) {

		ErrorDto json = new ErrorDto(404, "Resource: '" + path + "', not found.");
		return JsonUtils.toJson(json);
	}
}
