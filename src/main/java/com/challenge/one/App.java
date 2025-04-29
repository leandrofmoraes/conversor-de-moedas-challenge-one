package com.challenge.one;

import java.math.BigDecimal;

import com.challenge.one.controller.ModeController;

public class App {
  public static void main(String[] args) {

    ModeController controller = new ModeController();

    if (args.length == 0) {
      controller.interactiveMode();
    } else if (args.length == 1) {
      controller.handleSingleArgument(args[0]);
    } else if (args.length == 3) {
      String value = args[2].trim().replace(',', '.');
      controller.imperativeMode(args[0], args[1], new BigDecimal(value));
    } else {
      throw new IllegalArgumentException("Invalid number of arguments. Expected 0, 1 or 3 arguments.");
    }

  }
}
