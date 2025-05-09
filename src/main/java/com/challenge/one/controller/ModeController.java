package com.challenge.one.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.one.model.CurrencyModel;
import com.challenge.one.service.CurrencyService;
import com.challenge.one.util.ConsoleHelper;

public class ModeController {

  private CurrencyService currencyService;
  private List<CurrencyModel> currencies;
  private ConsoleHelper consoleHelper;
  private final Logger log = LoggerFactory.getLogger(ModeController.class);

  public ModeController() {
    this.currencyService = new CurrencyService();
    this.currencies = currencyService.getList();
    this.consoleHelper = new ConsoleHelper();
  }

  private void doConversion() {
    consoleHelper.printLine();
    System.out.println(consoleHelper.getMessage("input.params"));
    var inputFrom = consoleHelper.getInput("from.currency.select");
    CurrencyModel selectedCurrencyFrom = null;
    try {
      selectedCurrencyFrom = findCurrency(inputFrom);
    } catch (IllegalArgumentException e) {
      System.out.println(consoleHelper.getMessage("error.invalid"));
      String logMessage = String.format("[CONVERSION] Error message: %s | input: %s",
          e.getMessage(), inputFrom);
      log.info(logMessage);
      System.out.println(logMessage);
      consoleHelper.waitForUser();
      return;
    }

    BigDecimal inputValue = consoleHelper.getInputValue();

    var inputTo = consoleHelper.getInput("to.currency.select");
    CurrencyModel selectedCurrencyTo = null;
    try {
      selectedCurrencyTo = findCurrency(inputTo);
    } catch (IllegalArgumentException e) {
      System.out.println(consoleHelper.getMessage("error.invalid"));
      String logMessage = String.format("[CONVERSION] Error message: %s | input: %s",
          e.getMessage(), inputTo);
      log.info(logMessage);
      System.out.println(logMessage);
      consoleHelper.waitForUser();
      return;
    }

    BigDecimal convertedValue = currencyService.convertCurrency(
        selectedCurrencyFrom.getCode(), selectedCurrencyTo.getCode(), inputValue);

    consoleHelper.printLine();
    System.out.println(consoleHelper.getMessage("conversion.result",
        selectedCurrencyFrom.getSymbol(), inputValue, selectedCurrencyFrom.getName(),
        selectedCurrencyTo.getSymbol(), convertedValue, selectedCurrencyTo.getName()));

    consoleHelper.printLine();
    consoleHelper.waitForUser();
  }

  private void listCurrencies() {
    String lastUpdate = currencyService.getLastCurrencyQuoteUpdate();
    consoleHelper.printTitle();

    System.out.println("Last Update: " + lastUpdate);
    consoleHelper.printLine();
    consoleHelper.printCurrenciesList(currencies);
    consoleHelper.printLine();
    consoleHelper.waitForUser();
  }

  public void interactiveMode() {
    while (true) {
      consoleHelper.clearScreen();
      consoleHelper.printMenu();
      int choice = consoleHelper.getUserChoice();
      switch (choice) {
        case 1 -> validateAndRun(() -> listCurrencies());
        case 2 -> validateAndRun(() -> doConversion());
        case 3 -> {
          consoleHelper.clearScreen();
          consoleHelper.printHistory();
          consoleHelper.waitForUser();
        }
        case 4 -> {
          return;
        }
        default -> {
          System.out.println(consoleHelper.getMessage("menu.invalid"));
          String logMessage = String.format("[MENU] Error message: %s | input: %d",
              consoleHelper.getMessage("menu.invalid"), choice);
          log.info(logMessage);
          continue;
        }
      }
    }
  }

  private void validateAndRun(Runnable function) {
    if (currencies != null) {
      function.run();
    } else {
      System.out.println("Failed to retrieve currency data.");
    }
  }

  public void handleSingleArgument(String opt) {
    switch (opt) {
      case "--help", "-h" -> consoleHelper.printHelp();
      case "--history", "-H" -> consoleHelper.printHistory();
      case "--list", "-l" -> consoleHelper.printCurrenciesList(currencies);
      default -> {
        System.err.println("Opção desconhecida: " + opt);
        consoleHelper.printHelp();
        String logMessage = String.format("[Commandline Mode] Error message: %s | input: %s",
            consoleHelper.getMessage("error.invalid"), opt);
        log.info(logMessage);
      }
    }
  }

  public void commandLineMode(String input1, String input2, BigDecimal value) {

    var currencyFrom = findCurrency(input1);
    var currencyTo = findCurrency(input2);

    BigDecimal convertedValue = currencyService.convertCurrency(
        currencyFrom.getCode(), currencyTo.getCode(), value);

    System.out.println(consoleHelper.getMessage("conversion.result",
        currencyFrom.getSymbol(), value, currencyFrom.getName(),
        currencyTo.getSymbol(), convertedValue, currencyTo.getName()));
  }

  private CurrencyModel findCurrency(String str) {
    if (isNumeric(str)) {
      return currencyService.findCurrency(Integer.parseInt(str));
    } else {
      return currencyService.findCurrency(str);
    }
  }

  private boolean isNumeric(String str) {
    return str != null && str.matches("\\d+"); // Só números inteiros positivos
  }
}
