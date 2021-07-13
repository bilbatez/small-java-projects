package com.albertjtan.java.concurrency.threads;


public class NotThreadSafeExample {

  private static final CommonObject commonObject = new CommonObject();

  public static void main(String[] args) {
    for (int i = 0; i < 1000; i++) {
      int finalI = i;
      new Thread(() -> commonObject.doSomething(finalI)).start();
    }
  }

  private static class CommonObject {

    private static StringBuilder stringBuilder;

    public void doSomething(int i) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Starting ");
      stringBuilder.append("Process ");
      stringBuilder.append("Number :");
      stringBuilder.append(i);
      System.out.println(stringBuilder.toString());
    }

  }
}
