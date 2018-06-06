package com.zandero.rest.example.injection;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.zandero.rest.example.service.SlowService;
import com.zandero.rest.example.service.SlowServiceImpl;

/**
 *
 */
public class ServiceModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(SlowService.class).to(SlowServiceImpl.class);
	}
}
