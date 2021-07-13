package com.albertjtan.java.concurrency.threads;

/**
 * Implementing runnable is more recommended than extending a thread
 * since "extending" blocks the capability of inheritance from other necessary class
 * <p>
 * Thread started will be submitted to the default executorService (ForkJoinPool.commonPool)
 */
public class ThreadCreationExample {

  public static void main(String[] args) throws Exception {
    Thread thread = new ExampleThread(new ExampleRunnable());
    thread.start();
    thread.join();
  }

  private static class ExampleThread extends Thread {
    public ExampleThread(Runnable target) {
      super(target);
    }

    @Override
    public void run() {
      System.out.println(this.getName());
      System.out.println("Executing Thread");
      super.run();
      System.out.println("Finish Thread");
    }
  }

  private static class ExampleRunnable implements Runnable {
    @Override
    public void run() {
      System.out.println("Runnable");
    }
  }
}
