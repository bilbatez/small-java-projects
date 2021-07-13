package com.blibli.training.projectreactor.config;

import com.blibli.training.projectreactor.controller.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterConfig {

  @Bean
  public RouterFunction<ServerResponse> appContext() {
    return route()
        .GET("/", serverRequest -> ok()
            .body(Mono.just("learning-reactor"), String.class))
        .GET("/ping",
            serverRequest -> ok().body(Mono.just("pong"), String.class))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> userControllerRoute(UserController userController) {
    return route()
        .path("users", builder -> builder
            .GET("", userController::findAll)
            .GET("/id/{id}", userController::findById)
            .GET("/{username}", userController::findByUsername)
            .POST("/save", userController::save)
            .DELETE("/{id}", userController::delete)
            .build())
        .build();
  }

}
