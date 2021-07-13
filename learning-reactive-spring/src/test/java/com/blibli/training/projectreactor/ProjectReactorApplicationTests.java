package com.blibli.training.projectreactor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectReactorApplicationTests {

	@Autowired
	public WebTestClient testClient;

	@Test
	void appNameTest() {
		testClient.get().uri("/")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.isEqualTo("learning-reactor");
	}

	@Test
	void pingTest() {
		testClient.get().uri("/ping")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.isEqualTo("pong");
	}

}
