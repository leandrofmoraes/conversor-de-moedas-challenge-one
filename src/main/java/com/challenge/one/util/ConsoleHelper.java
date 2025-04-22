package com.challenge.one.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleHelper {

  private static final Scanner scanner = new Scanner(System.in);

  public static void printTitle(String title) {
    System.out.println("========================================");
    System.out.println(title);
    System.out.println("========================================");
  }

  public static void printMessage(String message) {
    System.out.println(message);
  }

  public static void printMenu(List<String> options) {

  }

  public static BigDecimal getInput() {

    while (true) {
      try {
        String input = scanner.nextLine().trim().replace(',', '.');
        return new BigDecimal(input);
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void close() {
    scanner.close();
  }
}
