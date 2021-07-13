package com.albertjtan.java.concurrency.building.blocks;

import java.util.concurrent.CyclicBarrier;

public class BarrierExample {

  private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

  public static void main(String[] args) throws Exception {
    new Human(1).start();
    new Human(2).start();
    Thread.sleep(3000);
    new Human(3).start();
    Thread.sleep(5000);
    new Human(4).start();
    new Human(5).start();
    Thread.sleep(3000);
    new Human(6).start();
  }

  private static class Human extends Thread {

    private int id;

    public Human(int id) {
      this.id = id;
    }

    @Override
    public void run() {
      try {
        System.out.println("Human " + id + " has arrived!");
        cyclicBarrier.await();
        System.out.println("PARTY TIME!!!");
      } catch (Exception ex) {

      }
    }
  }
}
