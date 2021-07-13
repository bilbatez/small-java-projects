package com.albertjtan.java.concurrency.sharing.objects;

/**
 * An Immutable Object
 */
public class ImmutableObjectExample {
  private final String logonId;
  private final String name;
  private final String phoneNumber;

  // If there is a field that holds reference to an object but declared final
  // the object still need to be immutable, else other code could still modify
  // this "immutable" object

  public ImmutableObjectExample(String logonId, String name, String phoneNumber) {
    this.logonId = logonId;
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

  public String getLogonId() {
    return logonId;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
