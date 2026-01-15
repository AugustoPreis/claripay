package com.augustopreis.claripay.config.seed.util;

public class SeederUtil {

  public static String userNameToEmail(String fullName) {
    String[] names = fullName.trim().toLowerCase().split(" ");

    String firstName = names[0].replaceAll("[^a-z0-9]", "");
    String lastName = names[names.length - 1].replaceAll("[^a-z0-9]", "");

    return firstName + "." + lastName + "@example.com";
  }
}
