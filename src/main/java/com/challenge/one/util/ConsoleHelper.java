package com.challenge.one.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.one.model.CurrencyModel;
import com.challenge.one.service.HistoryService;

public final class ConsoleHelper {

  private final ResourceBundle messages;
  private final Scanner scanner;
  private final Logger log = LoggerFactory.getLogger(ConsoleHelper.class);

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

  public void printCurrenciesList(List<CurrencyModel> currencyList) {

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

  public void waitForUser() {
    System.out.print("\n" + messages.getString("menu.continue.prompt")); // Mensagem localizada
    try {
      System.in.read();
      scanner.nextLine();
    } catch (IOException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public int getUserChoice() {
    int choice;
    String raw;
    while (true) {
      raw = getInput("menu.choose"); // lê como String
      try {
        choice = Integer.parseInt(raw);
        return choice;
      } catch (NumberFormatException e) {
        System.out.println(getMessage("menu.invalid"));

        String logMessage = String.format("[Choice input] Error message: %s",
            e.getMessage());
        log.info(logMessage);
        waitForUser();
        continue; // volta ao topo do while
      }
    }
  }

  public BigDecimal getInputValue() {
    while (true) {
      try {
        System.out.print(getMessage("get.currency.value") + " ");
        String input = scanner.next().trim().replace(',', '.');
        return new BigDecimal(input);
      } catch (NumberFormatException e) {
        System.out.println(getMessage("error.invalid"));
        String logMessage = String.format("[Input value] Error message: %s",
            e.getMessage());
        log.info(logMessage);
        waitForUser();
        continue;
      }
    }
  }

  public String getInput(String message) {
    while (true) {
      try {
        System.out.print(getMessage(message) + " ");
        String input = scanner.next();
        return input;
      } catch (Exception e) {
        System.out.println("Error: " + getMessage("error.invalid"));
        String logMessage = String.format("[Input value] Error message: %s",
            e.getMessage());
        log.info(logMessage);
        waitForUser();
        continue;
      }
    }
  }

  public void printMenu() {
    printTitle();
    System.out.println(messages.getString("menu.option.list"));
    System.out.println(messages.getString("menu.option.convert"));
    System.out.println(messages.getString("menu.option.history"));
    System.out.println(messages.getString("menu.option.exit"));
    // System.out.print(messages.getString("menu.choose") + " ");
  }

  public void clearScreen() {
    try {
      String os = System.getProperty("os.name").toLowerCase();
      ProcessBuilder pb;
      if (os.contains("windows")) {
        pb = new ProcessBuilder("cmd", "/c", "cls");
      } else {
        pb = new ProcessBuilder("clear");
      }
      pb.inheritIO().start().waitFor();
    } catch (Exception e) {
      // if it fails, print multiple blank lines as a fallback
      System.out.println("\n".repeat(50));
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

    printLine();
    System.out.println(helpText);
    printLine();
  }

  public void printLine() {
    System.out.println(
        "========================================================================================================================");
  }

  public void printHistory() {
    printLine();
    System.out.println(getMessage("history.title"));
    new HistoryService().getHistory().forEach(conversionDTO -> {
      System.out.println(getMessage("history.line",
          conversionDTO.timestamp(),
          conversionDTO.fromCurrency(),
          conversionDTO.toCurrency(),
          conversionDTO.amount(),
          conversionDTO.result()));
    });
    printLine();
  }
}
