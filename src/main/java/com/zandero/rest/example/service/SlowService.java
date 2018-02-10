package com.zandero.rest.example.service;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

/**
 *
 */
public class SlowService {

	public void wait(int seconds) {

		try {
			Thread.sleep(100 * seconds);
		}
		catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void async(Vertx vertx, Future<String> future, int time) {

		vertx.executeBlocking(
			fut -> {
				try {
					Thread.sleep(100 * time);
				}
				catch (InterruptedException e) {
					future.fail("Fail");
				}
				future.complete("Success");
				System.out.println("Success!");
				fut.complete();
			},
			false,
			fut -> {}
		);
	}
}
