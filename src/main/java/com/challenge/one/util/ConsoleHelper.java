package com.challenge.one.util;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.challenge.one.model.CurrencyModel;

public final class ConsoleHelper {

  private final ResourceBundle messages;
  private final Scanner scanner;

  public ConsoleHelper() {
    this.messages = LocalizationHelper.getMessages();
    this.scanner = new Scanner(System.in);
    this.scanner.useLocale(LocalizationHelper.getLocale());
  }

  public void printTitle() {
    String line = "======================================================";
    String title = getMessage("menu.title");
    int space = (line.length() - title.length()) / 2;

    System.out.printf("%s%n", line);
    System.out.printf("%-" + space + "s%n", title);
    System.out.printf("%s%n%n", line);
  }

  public String getMessage(String key) {
    return messages.getString(key);
  }

  // Método para mensagens com parâmetros
  public String getMessage(String key, Object... args) {
    return MessageFormat.format(messages.getString(key), args);
  }

  public void printMenuList(List<CurrencyModel> currencyList) {

    int col = 3;
    int row = (int) Math.ceil(currencyList.size() / (double) col);
    int maxTextLen = currencyList.stream()
        .mapToInt(currency -> currency.getName().length() + currency.getCode().length() + 4)
        .max()
        .orElse(20);

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        int index = i * col + j;
        if (index < currencyList.size()) {
          CurrencyModel currency = currencyList.get(index);
          System.out.printf("%3d) %-" + maxTextLen + "s", currency.getId(), currency.getCode() + " - "
              + currency.getName());
        }
      }
      System.out.println();
    }
  }

  public BigDecimal getInputValue() {
    while (true) {
      try {
        System.out.print(getMessage("get.currency.value") + " ");
        String input = scanner.next().trim().replace(',', '.');
        return new BigDecimal(input);
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public String getInput(String message) {
    while (true) {
      try {
        System.out.print(getMessage(message) + " ");
        String input = scanner.next();
        return input;
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void closeScanner() {
    scanner.close();
  }

  public void printHelp() {
    String helpText = MessageFormat.format("""
        {0}
        {1}
        {2}

        {3}

        {4}

        {5}

        {6}
        {7}
        {8}

        {9}
        """,
        messages.getString("help.title"),
        messages.getString("help.usage"),
        messages.getString("help.pattern"),
        messages.getString("help.params"),
        messages.getString("help.options"),
        messages.getString("help.interactive"),
        messages.getString("help.examples"),
        messages.getString("help.example_code"),
        messages.getString("help.example_index"),
        messages.getString("help.notes"));

    System.out.println(helpText);
  }
}
