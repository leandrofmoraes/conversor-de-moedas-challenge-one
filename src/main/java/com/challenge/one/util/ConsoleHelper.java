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

  public static void printMenuList(List<String> currencyList) {

    int col = 3;
    int row = (int) Math.ceil(currencyList.size() / (double) col);
    int maxTextLen = currencyList.stream()
        .mapToInt(String::length)
        .max()
        .orElse(20);
    int cellWidth = maxTextLen + 4;

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        int index = i * col + j;
        if (index < currencyList.size()) {
          // System.out.print(index + " - " + currencyList.get(index) + "\t");
          System.out.printf("%3d) %-" + cellWidth + "s", index, currencyList.get(index));
        }
      }
      System.out.println();
    }
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
