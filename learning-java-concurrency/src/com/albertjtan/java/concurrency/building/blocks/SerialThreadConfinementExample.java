package com.albertjtan.java.concurrency.building.blocks;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This is a bad example of handing ownership
 * a producer thread should ends when handing the object to the queue
 * modifying it on the producer thread will return unexpected result
 */
public class SerialThreadConfinementExample {

  private static final BlockingQueue<Record> queue = new LinkedBlockingQueue<>();

  public static void main(String[] args) throws Exception {
    new ConsumerThread().start();
    new ProducerThread().start();
  }

  private static class ConsumerThread extends Thread {

    @Override
    public void run() {
      try {
        Record record = queue.take();
        record.setName("Modified On Consumer");
        Thread.sleep(1000);
        record.print();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private static class ProducerThread extends Thread {

    @Override
    public void run() {
      try {
        Record record = new Record("SDE", "Budi");
        queue.put(record);
        record.setTitle("Modified on Producer");
        Thread.sleep(1000);
        record.print();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      } finally {
      }
    }
  }

  private static class Record {
    private String name;
    private String title;

    public Record(String name, String title) {
      this.name = name;
      this.title = title;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public void print() {
      System.out.println(title + " " + name);
    }
  }
}
