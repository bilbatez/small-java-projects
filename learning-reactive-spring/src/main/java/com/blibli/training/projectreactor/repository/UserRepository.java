package com.blibli.training.projectreactor.repository;

import com.blibli.training.projectreactor.model.repository.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

  Mono<User> findByUsername(String username);
}
