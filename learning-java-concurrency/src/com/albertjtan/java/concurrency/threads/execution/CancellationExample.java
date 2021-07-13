package com.albertjtan.java.concurrency.threads.execution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CancellationExample {
  private static final ExecutorService executorService = Executors.newCachedThreadPool();

  public static void main(String[] args) throws Exception {
    cancelViaFuture();
  }

  private static void interruptingThread() {
    Thread thread = new Thread(() -> {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        System.out.println("Interrupted!!");
        e.printStackTrace();
      }
    });
    thread.start();
    thread.interrupt();
  }

  private static void cancelViaFuture() throws Exception {
    Future future = executorService.submit(() -> {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        System.out.println("Interrupted!!!");
        e.printStackTrace();
      }
    });
    try {
      future.get(1, TimeUnit.SECONDS);
    } catch (TimeoutException ex) {

    } finally {
      future.cancel(true);
      Thread.sleep(1000);
      if (future.isCancelled()) {
        System.out.println("Cancelled!!");
        executorService.shutdown();
      }
    }
  }
}
