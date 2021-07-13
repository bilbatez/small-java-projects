package com.albertjtan.java.concurrency.building.blocks;

import java.util.concurrent.Semaphore;

public class SemaphoresExample {

  private static final Semaphore semaphore = new Semaphore(3);

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      new DaemonThread(i, 5000 + i * 1000).start();
    }

  }

  private static class DaemonThread extends Thread {

    private int id;
    private int duration;

    public DaemonThread(int id, int duration) {
      this.id = id;
      this.duration = duration;
    }

    @Override
    public void run() {
      try {
        semaphore.acquire();
        System.out.println("Start task with id : " + id + " and duration : " + duration);
        Thread.sleep(duration);
      } catch (Exception ex) {
        System.out.println("Error : " + ex.getMessage());
        ex.printStackTrace();
      } finally {
        System.out.println("Finish task with id : " + id + " and duration : " + duration);
        semaphore.release();
      }
    }
  }

}
