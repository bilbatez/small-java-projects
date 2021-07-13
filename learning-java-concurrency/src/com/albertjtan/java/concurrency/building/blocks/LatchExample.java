package com.albertjtan.java.concurrency.building.blocks;

import java.util.concurrent.CountDownLatch;

public class LatchExample {

  private static final CountDownLatch startupLatch = new CountDownLatch(1);
  private static final CountDownLatch shutdownLatch = new CountDownLatch(2);

  public static void main(String[] args) {
    new LastDaemon().start();
    new DaemonThread(1000).start();
    new DaemonThread(5000).start();
    new FirstDaemon().start();
  }

  private static class FirstDaemon extends Thread {

    @Override
    public void run() {
      try {
        System.out.println("Intializing Resource...");
        Thread.sleep(2000);
        System.out.println("Finish initializing resource!");
      } catch (Exception ex) {

      } finally {
        startupLatch.countDown();
      }
    }
  }


  private static class DaemonThread extends Thread {

    private int duration;

    public DaemonThread(int duration) {
      this.duration = duration;
    }

    @Override
    public void run() {
      try {
        startupLatch.await();
        System.out.println("Start task with duration : " + duration);
        Thread.sleep(duration);
      } catch (Exception ex) {

      } finally {
        System.out.println("Finish task with duration : " + duration);
        shutdownLatch.countDown();
      }
    }
  }

  private static class LastDaemon extends Thread {

    @Override
    public void run() {
      try {
        shutdownLatch.await();
      } catch (Exception ex) {
      } finally {
        System.out.println("Shutting Down");
      }
    }
  }

}
