package com.zandero.rest.example.rest;

import com.zandero.rest.example.rest.dto.ErrorDto;
import com.zandero.rest.exception.ExceptionHandler;
import com.zandero.utils.StringUtils;
import com.zandero.utils.extra.JsonUtils;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;

/**
 * Common purpose error handler for any REST that produces an exception
 */
@Produces("application/json;charset=UTF-8")
public class RestErrorHandler implements ExceptionHandler<Throwable> {

	private static final Logger log = LoggerFactory.getLogger(RestErrorHandler.class);

	@Override
	public void write(Throwable result, HttpServerRequest request, HttpServerResponse response) {

		log.error("Request failed: ", result);

		response.setStatusCode(400);

		String message = result.getMessage();
		if (StringUtils.isNullOrEmptyTrimmed(message)) {
			message = result.toString();
		}

		response.end(JsonUtils.toJson(new ErrorDto(400, message)));
	}
}
