package com.order_service.order_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest(classes = OrderServiceApplication.class)
@EntityScan(basePackages = {"com.order_service.order_service.entity"})
@EnableJpaRepositories(basePackages = {"com.order_service.order_service.repository"})
class OrderServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
