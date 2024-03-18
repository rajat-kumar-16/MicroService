package com.example.demo.Config;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.*;
@Configuration
public class WebClientConfig {
	@Bean
	public WebClient webClient()
	{
		return WebClient.builder().build();
	}
}
