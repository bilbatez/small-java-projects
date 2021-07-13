package com.albertjtan.java.concurrency.building.blocks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicClassesExample {
  private static SafeCounterWithoutLock counter = new SafeCounterWithoutLock();

  public static class SafeCounterWithoutLock {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final AtomicInteger counter = new AtomicInteger(0);

    public int getValue() {
      return counter.get();
    }

    public void increment() {
      while (true) {
        int existingValue = getValue();
        int newValue = existingValue + 1;
        if (counter.compareAndSet(existingValue, newValue)) {
          return;
        }
      }
    }
  }

  public static void main(String[] args) {
    for (int i = 0; i < 10000; i++) {
      new Thread(() -> counter.increment()).start();
    }
    ForkJoinPool.commonPool().shutdown();
    System.out.println("Current Value: " + counter.getValue());
  }

}
