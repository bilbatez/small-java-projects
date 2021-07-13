package com.albertjtan.java.concurrency.threads.safety;

public class ThreadSafeExample {

  public static void main(String[] args) {
    StatelessObject statelessObject = new StatelessObject();
    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < 100; j++) {
        int finalI = j;
        new Thread(() -> statelessObject.doSomething(String.valueOf(finalI))).start();
      }
    }
  }

  // This is a stateless object where there are no invariants
  // This class is always Thread Safe even though using a non thread safe class
  private static class StatelessObject {
    public void doSomething(String message) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Start ");
      stringBuilder.append("Running ");
      stringBuilder.append("Process :");
      stringBuilder.append(message);
      System.out.println(stringBuilder.toString());
    }
  }

}
