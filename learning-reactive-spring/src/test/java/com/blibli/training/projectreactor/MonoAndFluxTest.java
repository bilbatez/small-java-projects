package com.blibli.training.projectreactor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MonoAndFluxTest {

  @Test
  void createMonoExample() {
    Mono<String> name = Mono.just("albert");

    StepVerifier.create(name)
        .expectNext("albert")
        .verifyComplete();
  }

  @Test
  void createFluxExample() {
    Flux<String> fruits = Flux.just("apple", "banana", "kiwi");

    StepVerifier.create(fruits)
        .expectNext("apple")
        .expectNext("banana")
        .expectNext("kiwi")
        .verifyComplete();
  }

  @Test
  void mergeFluxesExample() {
    Flux<String> names = Flux.just("albert", "budi", "chiki")
        .delayElements(Duration.ofMillis(500));
    Flux<String> fruits = Flux.just("kiwi", "apple", "banana")
        .delayElements(Duration.ofMillis(500))
        .delaySubscription(Duration.ofMillis(250));

    Flux<String> combined = names.mergeWith(fruits);

    StepVerifier.create(combined)
        .expectNext("albert")
        .expectNext("kiwi")
        .expectNext("budi")
        .expectNext("apple")
        .expectNext("chiki")
        .expectNext("banana")
        .verifyComplete();
  }

  @Test
  void zipFluxesExample() {
    Flux<String> names = Flux.just("albert", "budi", "chiki")
        .delayElements(Duration.ofMillis(500));
    Flux<String> fruits = Flux.just("kiwi", "apple", "banana")
        .delayElements(Duration.ofMillis(500))
        .delaySubscription(Duration.ofMillis(250));

    Flux<Tuple2<String, String>> zipped = names.zipWith(fruits);

    StepVerifier.create(zipped)
        .expectNextMatches(res -> "albert".equals(res.getT1()) && "kiwi".equals(res.getT2()))
        .expectNextMatches(res -> "budi".equals(res.getT1()) && "apple".equals(res.getT2()))
        .expectNextMatches(res -> "chiki".equals(res.getT1()) && "banana".equals(res.getT2()))
        .verifyComplete();
  }

  @Test
  void firstFluxesExample() {
    Flux<String> names = Flux.just("albert", "budi", "chiki")
        .delayElements(Duration.ofMillis(500));
    Flux<String> fruits = Flux.just("kiwi", "apple", "banana")
        .delayElements(Duration.ofMillis(500))
        .delaySubscription(Duration.ofMillis(250));

    Flux<String> first = Flux.firstWithSignal(names, fruits);

    StepVerifier.create(first)
        .expectNext("albert")
        .expectNext("budi")
        .expectNext("chiki")
        .verifyComplete();
  }

  @Test
  void mapExample() {
    Flux<String> names = Flux.just("albert", "budi", "chiki");

    Flux<String> upperCase = names
        .map(String::toUpperCase);

//    upperCase.log().subscribe();

    StepVerifier.create(upperCase)
        .expectNext("ALBERT")
        .expectNext("BUDI")
        .expectNext("CHIKI")
        .verifyComplete();
  }

  @Test
  void flatMapExample() {
    List<String> arrayNames = Arrays.asList(new String[]{"albert", "budi", "chiki"});

    Flux<String> names = Flux.fromIterable(arrayNames);

    Flux<String> upperCase = names
        .flatMap(name -> Mono.just(name)
            .map(String::toUpperCase)
            .subscribeOn(Schedulers.parallel()));

//    upperCase.log().subscribe();

    StepVerifier.create(upperCase)
        .assertNext(res -> arrayNames.contains(res))
        .assertNext(res -> arrayNames.contains(res))
        .assertNext(res -> arrayNames.contains(res))
        .verifyComplete();
  }

  @Test
  void takeExample() {
    Flux<String> names = Flux.just("albert", "budi", "chiki");

    Flux<String> taken = names.take(2);

    StepVerifier.create(taken)
        .expectNext("albert")
        .expectNext("budi")
        .verifyComplete();
  }

  @Test
  void skipExample() {
    Flux<String> names = Flux.just("albert", "budi", "chiki");

    Flux<String> skipped = names.skip(2);

    StepVerifier.create(skipped)
        .expectNext("chiki")
        .verifyComplete();
  }

  @Test
  void filterExample() {
    Flux<String> names = Flux.just("albert", "budi", "chiki");

    Flux<String> filter = names.filter(res -> "budi".equals(res));

    StepVerifier.create(filter)
        .expectNext("budi")
        .verifyComplete();
  }

  @Test
  void anyExample() {
    Flux<String> names = Flux.just("albert", "budi", "chiki");

    Mono<Boolean> result = names.any(res -> res.contains("b"));

    StepVerifier.create(result)
        .expectNext(true)
        .verifyComplete();
  }

  @Test
  void allExample() {

    Flux<String> names = Flux.just("albert", "budi", "chiki");

    Mono<Boolean> result = names.all(res -> res.contains("b"));

    StepVerifier.create(result)
        .expectNext(false)
        .verifyComplete();
  }
}
