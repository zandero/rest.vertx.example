package com.zandero.rest.example.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

/**
 *
 */
public interface SlowService {
	void wait(int seconds);

	void async(Vertx vertx, Future<String> future, int time);
}
