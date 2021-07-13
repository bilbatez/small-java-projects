package com.albertjtan.java.concurrency.sharing.objects;

public class JavaReorderingExample {

  private static Integer number = 0;
  private static boolean ready = false;

  private static class ReaderThread extends Thread {
    @Override
    public void run() {
      while (!ready) {
        System.out.println("Error");
      }
      System.out.println(number);
    }
  }

  /**
   * Thread.sleep is to affect the java reordering logic
   * if commented the operation will almost always likely to return 100
   */
  public static void main(String[] args) throws Exception {
    new ReaderThread().start();
//        Thread.sleep(1);
    number = 100;
    ready = true;
  }
}
