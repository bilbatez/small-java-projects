package com.albertjtan.data;

import java.nio.charset.StandardCharsets;

public class SampleString {
  public static final byte[] LOREM_20kb;
  public static final byte[] LOREM_125kb;
  public static final byte[] LOREM_1mb;

  static {
    LOREM_20kb = readFile("lorem_20kb.txt");
    LOREM_125kb = readFile("lorem_125kb.txt");
    LOREM_1mb = readFile("lorem_1mb.txt");
  }

  private static byte[] readFile(String file) {
    return SampleString.class
        .getClassLoader()
        .getResource(file)
        .getFile()
        .getBytes(StandardCharsets.UTF_8);
  }
}
