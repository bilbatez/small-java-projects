package com.albertjtan.java.concurrency.threads.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {
  private static ExecutorService cachedExecutor = Executors.newCachedThreadPool();
  private static ExecutorService fixedExecutor =
      Executors.newFixedThreadPool(4);
  private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
  private static ExecutorService scheduledExecutor =
      Executors.newScheduledThreadPool(4);
  private static ExecutorService boundedExecutor =
      new ThreadPoolExecutor(0, 100, 60, TimeUnit.SECONDS, new SynchronousQueue<>());

  public static void main(String[] args) throws Exception {
    taskExecutionWithExecutorService();
    taskExecutionWithCompletableFutures();
    taskExecutionWithCompletionQueue();

    shutdown();
  }

  public static void shutdown() {
    cachedExecutor.shutdown();
    fixedExecutor.shutdown();
    singleThreadExecutor.shutdown();
    scheduledExecutor.shutdown();
    boundedExecutor.shutdown();
  }

  private static void taskExecutionWithExecutorService() {
    List<Future<Boolean>> futures = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      futures.add(boundedExecutor.submit(() -> true));
    }

    futures.forEach(future -> {
      try {
        Boolean value = future.get();
        System.out.println(value);
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    });
  }

  private static void taskExecutionWithCompletableFutures() {
    for (int i = 0; i < 10; i++) {
      int finalI = i;
      CompletableFuture
          .supplyAsync(() -> "hello" + finalI, boundedExecutor)
          .thenAccept(System.out::println);
    }
  }

  private static void taskExecutionWithCompletionQueue() throws Exception {
    ExecutorCompletionService<Boolean> executor =
        new ExecutorCompletionService<>(boundedExecutor, new LinkedBlockingQueue<>(100));
    for (int i = 0; i < 100; i++) {
      executor.submit(() -> true);
    }

    while (true) {
      executor.poll(1, TimeUnit.SECONDS);
    }
  }

}
