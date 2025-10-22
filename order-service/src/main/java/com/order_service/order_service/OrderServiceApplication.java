package com.order_service.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
		"com.order_service.order_service",
		//"com.product_service.product_service"//not recommended to add product-service as it creates
		// tight coupling instead create a common folder and share the common files
		// for example Product ENtity in this case
})
@EntityScan(basePackages = {"com.commonFiles.commonFiles.entity", "com.order_service.order_service.entity"})
@EnableJpaRepositories(basePackages = {"com.order_service.order_service.repository"})
public class OrderServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> factory) {
		return new KafkaTemplate<>(factory);
	}

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		return builder.build();
	}

}
