package com.zandero.rest.example.service;

import com.zandero.rest.data.RouteDefinition;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SlowServiceImpl implements SlowService {

	private final static Logger log = LoggerFactory.getLogger(SlowServiceImpl.class);

	private final WorkerExecutor executor;

	public SlowServiceImpl() {
		executor = Vertx.vertx().createSharedWorkerExecutor("SlowServiceExecutor", 20);
	}

	@Override
	public void wait(int seconds) {

		try {
			log.info("Starting sleep: " + 100 * seconds + " ms");
			Thread.sleep(100 * seconds);
			log.info("Sleep ended!");
		}
		catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void async(Vertx vertx, Future<String> future, int time) {

		executor.executeBlocking(
			fut -> {
				try {
					log.info("Starting ASYNC sleep: " + 100 * time + " ms");
					Thread.sleep(100 * time);
				}
				catch (InterruptedException e) {
					future.fail("Fail");
				}
				future.complete("Success");
				//System.out.println("Success!");
				fut.complete();
				log.info("ASYNC sleep ended!");
			},
			false,
			fut -> {}
		);
	}
}
