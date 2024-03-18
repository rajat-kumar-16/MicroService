package com.example.GatewayService;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@FeignClient(value= "Gateway-Service")
@EnableDiscoveryClient
public class GatewayServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("Transaction", r -> r.path("/Transaction/**")
						.filters(f -> f.stripPrefix(1))
						.uri("lb://Transaction-Service"))
				.route("User", r -> r.path("/User/**")
						.filters(f -> f.stripPrefix(1))
						.uri("lb://User-Service"))
				.build();
	}
}
