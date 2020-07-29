package com.zandero.rest.example.service;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;

/**
 *
 */
public interface SlowService {
    void wait(int seconds);

    void async(Vertx vertx, Promise<String> future, int time);
}
