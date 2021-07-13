package com.albertjtan.java.concurrency.threads.execution;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DeadlockExample {
  private static final ExecutorService executor = Executors.newSingleThreadExecutor();

  public static void main(String[] args) {
    simpleDeadlock();
  }

  private static void simpleDeadlock() {
    executor.submit(() -> {
      Future<Boolean> result = executor.submit(() -> true);
      try {
        result.get(5, TimeUnit.SECONDS);
      } catch (TimeoutException ex) {
        System.out.println("Deadlock!!");
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      } finally {
        result.cancel(true);
        if (result.isCancelled()) {
          executor.shutdown();
        }
      }
    });
  }
}
