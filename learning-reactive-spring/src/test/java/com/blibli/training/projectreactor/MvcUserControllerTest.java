package com.blibli.training.projectreactor;

import com.blibli.training.projectreactor.model.controller.request.UserRequest;
import com.blibli.training.projectreactor.model.controller.response.UserResponse;
import com.blibli.training.projectreactor.model.repository.User;
import com.blibli.training.projectreactor.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MvcUserControllerTest {

  @Autowired
  private WebTestClient testClient;

  @Autowired
  private UserRepository userRepository;

  @AfterEach
  void tearDown() {
    userRepository.deleteAll().subscribe();
  }

  @Test
  void findAll_success_shouldReturnResponse() {
    List<User> users = Arrays.asList(
        new User("albert"),
        new User("budi"),
        new User("chiki")
    );
    userRepository.saveAll(users).subscribe();

    testClient.get().uri("/mvc/users/")
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<List<UserResponse>>() {
        })
        .isEqualTo(users.stream().map(User::toUserResponse).collect(Collectors.toList()));
  }

  @Test
  void findById_success_shouldReturnResponse() {
    List<User> users = Arrays.asList(
        new User("1", "albert"),
        new User("2", "budi"),
        new User("3", "chiki")
    );
    userRepository.saveAll(users).subscribe();

    testClient.get().uri("/mvc/users/id/2")
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<User>() {
        })
        .isEqualTo(new User("2", "budi"));
  }

  @Test
  void findByUsername_success_shouldReturnResponse() {
    List<User> users = Arrays.asList(
        new User("1", "albert"),
        new User("2", "budi"),
        new User("3", "chiki")
    );
    userRepository.saveAll(users).subscribe();

    testClient.get().uri("/mvc/users/budi")
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<User>() {
        })
        .isEqualTo(new User("2", "budi"));
  }

  @Test
  void save_success_shouldReturnResponse() {
    User result = testClient.post().uri("/mvc/users/save")
        .body(Mono.just(new UserRequest("albert")), UserRequest.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody(new ParameterizedTypeReference<User>() {
        }).returnResult().getResponseBody();

    assertEquals("albert", result.getUsername());

    StepVerifier.create(userRepository.findById(result.getId()))
        .assertNext(u -> "albert".equals(u.getUsername()))
        .verifyComplete();
  }

  @Test
  void delete_success_shouldReturnResponse() {
    userRepository.save(new User("1", "albert")).subscribe();

    testClient.delete().uri("/mvc/users/1")
        .exchange()
        .expectStatus().isOk();

    StepVerifier.create(userRepository.findById("1"))
        .verifyComplete();
  }

}
