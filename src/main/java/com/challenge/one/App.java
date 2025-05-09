package com.challenge.one;

import java.math.BigDecimal;

import com.challenge.one.controller.ModeController;

public class App {
  public static void main(String[] args) {

    ModeController controller = new ModeController();

    switch (args.length) {
      case 0 -> controller.interactiveMode();
      case 1 -> controller.handleSingleArgument(args[0]);
      case 3 -> {
        String value = args[2].trim().replace(',', '.');
        controller.commandLineMode(args[0], args[1], new BigDecimal(value));
      }
      default ->
        throw new IllegalArgumentException("Invalid number of arguments. Expected 0, 1 or 3 arguments.");
    }
  }
}
