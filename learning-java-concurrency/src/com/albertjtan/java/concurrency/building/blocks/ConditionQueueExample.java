package com.albertjtan.java.concurrency.building.blocks;

import java.util.concurrent.ForkJoinPool;

public class ConditionQueueExample {
  private static class SomeRandomPojo {
    private boolean updatable;
    private int value;
    private final Object lock = new Object();

    public SomeRandomPojo(boolean updatable, int value) {
      this.updatable = updatable;
      this.value = value;
    }

    public void increment() throws Exception {
      synchronized (lock) {
        while (!updatable) {
          // Accessing thread will be parked
          // wait must always be wrapped in a condition loop
          lock.wait();
        }
        value++;
      }
    }

    public void setUpdatable(boolean value) {
      synchronized (lock) {
        this.updatable = value;
        lock.notifyAll();
      }
    }

    public int getValue() {
      return value;
    }

  }

  public static void main(String[] args) throws Exception {
    SomeRandomPojo obj = new SomeRandomPojo(false, 0);
    new Thread(() -> {
      try {
        obj.increment();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
    System.out.println("Current Value:" + obj.getValue());
    Thread.sleep(3000);
    obj.setUpdatable(true);
    Thread.sleep(100);
    System.out.println("Current Value:" + obj.getValue());
    ForkJoinPool.commonPool().shutdown();
  }

}
