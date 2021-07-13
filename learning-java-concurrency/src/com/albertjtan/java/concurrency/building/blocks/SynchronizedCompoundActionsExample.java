package com.albertjtan.java.concurrency.building.blocks;

import java.util.Vector;

/**
 * Try to remove the synchronized method and try it a few times
 * you will get arrayIndexOutOfBoundException after a few try
 * <p>
 * The shared class is thread safe since it will never show corrupted values
 * However the compound actions can cause unexpected behaviour without proper
 * client side locking
 */
public class SynchronizedCompoundActionsExample {

  private static final Vector<Integer> numbers = new Vector<>();

  public static void main(String[] args) throws Exception {
    for (int i = 0; i < 10000; i++) {
      numbers.add(i);
    }

    for (int i = 0; i < 9999; i++) {
      new Thread(() -> deleteLast(numbers)).start();
      new Thread(() -> getLast(numbers)).start();
    }
  }

  private static Integer getLast(Vector<Integer> numbers) {
    synchronized (numbers) {
      int lastIndex = numbers.size() - 1;
      return numbers.get(lastIndex);
    }
  }

  private static void deleteLast(Vector<Integer> numbers) {
    synchronized (numbers) {
      int lastIndex = numbers.size() - 1;
      numbers.remove(lastIndex);
    }
  }

}
