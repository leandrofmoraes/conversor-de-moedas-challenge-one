package com.challenge.one.util;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.challenge.one.model.CurrencyModel;

public final class ConsoleHelper {

  private final ResourceBundle messages;

  public ConsoleHelper() {
    this.messages = LocalizationHelper.getMessages();
  }

  private final Scanner scanner = new Scanner(System.in);

  public void printTitle() {
    System.out.println("========================================");
    System.out.println(getMessage("menu.title"));
    System.out.println("========================================");
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
        String input = scanner.nextLine().trim().replace(',', '.');
        return new BigDecimal(input);
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public int getInputId() {
    while (true) {
      try {
        int input = scanner.nextInt();
        return input;
      } catch (NumberFormatException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  public void close() {
    scanner.close();
  }
}
