package com.albertjtan.java.concurrency.threads.safety;

public class SynchronizedExample {

  public static void main(String[] args) {
    Counter counter = new Counter();
    for (int i = 0; i < 100; i++) {
      new Thread(counter::increment).start();
    }
  }


  public static class Counter {
    private int value = 0;

    // will use the instance of itself as lock
    public synchronized void increment() {
      value++;
      // Reentrancy example
      // Happens when lock is already acquired
      System.out.println("Current value :" + getValue());
    }

    //  #Partial synchronized example
    //  public void increment() {
    //        synchronized (this) {
    //            value++;
    //        }
    //        System.out.println("Current value :" + getValue());
    //  }

    public synchronized int getValue() {
      return value;
    }
  }
}
