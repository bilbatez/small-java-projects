package com.blibli.training.projectreactor.controller.mvc;

import com.blibli.training.projectreactor.model.controller.request.UserRequest;
import com.blibli.training.projectreactor.model.repository.User;
import com.blibli.training.projectreactor.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mvc/users")
@AllArgsConstructor
public class MvcUserController {

  private final UserRepository userRepository;

  @GetMapping
  public Flux<User> findAll() {
    return userRepository.findAll();
  }

  @GetMapping("/id/{id}")
  public Mono<User> findById(@PathVariable("id") String id) {
    return userRepository.findById(id);
  }

  @GetMapping("/{username}")
  public Mono<User> findByUsername(@PathVariable("username") String username) {
    return userRepository.findByUsername(username);
  }

  @PostMapping("/save")
  public Mono<User> save(@RequestBody UserRequest user){
    return userRepository.save(user.toUser());
  }

  @DeleteMapping("/{id}")
  public Mono<Boolean> delete(@PathVariable("id") String id) {
    userRepository.deleteById(id).subscribe();
    return Mono.just(true);
  }
}
