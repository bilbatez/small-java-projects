package com.albertjtan.java.concurrency.building.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SynchronizedCollectionExample {

  /**
   * By using Collections.synchronized... method
   * All of this class methods will be wrapped and provided with a locking mechanism
   * It may provide safety but arguable not the best choice since there is performance deterioration
   */
  private static final List<Integer> numbers = Collections.synchronizedList(new ArrayList<>());
  private static final Set<Integer> uniqueNumbers = Collections.synchronizedSet(new HashSet<>());

  private static final Map<Integer, Record> keyWithValues = Collections.synchronizedMap(new HashMap<>());


  public static void main(String[] args) {
    new Thread(() -> keyWithValues.putIfAbsent(1, new Record("SDE", "Budi"))).start();
    new Thread(() -> keyWithValues.putIfAbsent(2, new Record("SDET", "Tono"))).start();
    new Thread(() -> keyWithValues.putIfAbsent(3, new Record("CS", "Sono"))).start();

    for (int i = 0; i < 100; i++) {
      new Thread(() -> {
        Record record = keyWithValues.get(ThreadLocalRandom.current().nextInt(3) + 1);
        record.printFullName();
      }).start();
    }
  }

  private static class Record {
    private String title;
    private String name;

    public Record(String title, String name) {
      this.title = title;
      this.name = name;
    }

    private void printFullName() {
      System.out.println(title + " " + name);
    }
  }

}
