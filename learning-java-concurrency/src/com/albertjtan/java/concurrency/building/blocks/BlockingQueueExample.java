package com.albertjtan.java.concurrency.building.blocks;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueExample {

  private static final BlockingQueue<Boolean> queue = new LinkedBlockingQueue<>();

  public static void main(String[] args) throws Exception {
    new ConsumerThread().start();
    Thread.sleep(3000);
    new ProducerThread().start();
  }

  private static class ConsumerThread extends Thread {

    @Override
    public void run() {
      System.out.println("Start Consumer!");
      while (true) {
        try {
          if (queue.take())
            System.out.println("Ping!");
          else
            System.out.println("Pong!");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private static class ProducerThread extends Thread {
    private static Boolean value = false;

    @Override
    public void run() {
      System.out.println("Start Producer!");
      while (true) {
        try {
          Thread.sleep(2000);
          queue.put(!value);
        } catch (InterruptedException ex) {
          ex.printStackTrace();
        } finally {
          value = !value;
        }
      }
    }
  }

}
