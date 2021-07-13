package com.albertjtan.java.concurrency.sharing.objects;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalExample {

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      new Thread(new HeavyTask()).start();
    }
  }

  private static class ThreadId {
    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId = ThreadLocal.withInitial(() -> nextId.getAndIncrement());

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
      return threadId.get();
    }
  }

  private static class HeavyTask implements Runnable {

    @Override
    public void run() {
      int uniqueId = ThreadId.get();
      System.out.println("First ID : " + uniqueId);
    }
  }

}
