package com.hotmart.challenge.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotmart.challenge.externalapi.model.CallApiService;

@Configuration
public class CallApiServiceConfig {

	@Bean
	public CallApiService callApiService() {
		return new CallApiService();
	}
}