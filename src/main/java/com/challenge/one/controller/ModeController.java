package com.challenge.one.controller;

import java.math.BigDecimal;
import java.util.List;

import com.challenge.one.model.CurrencyModel;
import com.challenge.one.service.CurrencyService;
import com.challenge.one.util.ConsoleHelper;

public class ModeController {

  CurrencyService currencyService;
  List<CurrencyModel> currencies;
  ConsoleHelper consoleHelper;

  public ModeController() {
    this.currencyService = new CurrencyService();
    this.currencies = currencyService.getList();
    this.consoleHelper = new ConsoleHelper();
  }

  public void interactiveMode() {
    String lastUpdate = currencyService.getLastCurrencyQuoteUpdate();
    consoleHelper.printTitle();

    if (currencies != null) {
      System.out.println("Last Update: " + lastUpdate);
      consoleHelper.printMenuList(currencies);

      var inputFrom = consoleHelper.getInput("from.currency.select");
      CurrencyModel selectedCurrencyFrom = findCurrency(inputFrom);

      BigDecimal inputValue = consoleHelper.getInputValue();

      var inputTo = consoleHelper.getInput("to.currency.select");
      CurrencyModel selectedCurrencyTo = findCurrency(inputTo);

      BigDecimal convertedValue = currencyService.convertCurrency(
          selectedCurrencyFrom.getCode(), selectedCurrencyTo.getCode(), inputValue);

      System.out.println(consoleHelper.getMessage("conversion.result",
          selectedCurrencyFrom.getSymbol(), inputValue, selectedCurrencyFrom.getName(),
          selectedCurrencyTo.getSymbol(), convertedValue, selectedCurrencyTo.getName()));

      // consoleHelper.closeScanner(); // Scanner is no longer needed
    } else {
      System.out.println("Failed to retrieve currency data.");
    }
  }

  public void handleSingleArgument(String opt) {
    switch (opt) {
      case "--help", "-h" -> consoleHelper.printHelp();
      case "--list", "-l" -> consoleHelper.printMenuList(currencies);
      default -> {
        System.err.println("Opção desconhecida: " + opt);
        consoleHelper.printHelp();
      }
    }
  }

  public void imperativeMode(String input1, String input2, BigDecimal value) {

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
