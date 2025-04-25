package com.challenge.one;

import java.math.BigDecimal;
import java.util.List;

import com.challenge.one.model.CurrencyModel;
import com.challenge.one.service.CurrencyService;
import com.challenge.one.util.ConsoleHelper;

public class App {
  public static void main(String[] args) {

    CurrencyService currencyService = new CurrencyService();
    List<CurrencyModel> currencies = currencyService.getList();
    ConsoleHelper consoleHelper = new ConsoleHelper();

    String lastUpdate = currencyService.getLastCurrencyQuoteUpdate();
    consoleHelper.printTitle();

    if (currencies != null) {
      System.out.println("Last Update: " + lastUpdate);
      consoleHelper.printMenuList(currencies);

      System.out.print(consoleHelper.getMessage("from.currency.select") + " ");
      int inputFromIndex = consoleHelper.getInputId();
      CurrencyModel selectedCurrencyFrom = currencyService.getCurrencyById(inputFromIndex);

      System.out.print(consoleHelper.getMessage("get.currency.value") + " ");
      BigDecimal inputValue = consoleHelper.getInputValue();

      System.out.print(consoleHelper.getMessage("to.currency.select") + " ");
      int inputToIndex = consoleHelper.getInputId();
      CurrencyModel selectedCurrencyTo = currencyService.getCurrencyById(inputToIndex);

      BigDecimal convertedValue = currencyService.convertCurrency(
          selectedCurrencyFrom.getCode(), selectedCurrencyTo.getCode(), inputValue);

      System.out.println(consoleHelper.getMessage("conversion.result",
          selectedCurrencyFrom.getName(), selectedCurrencyFrom.getSymbol(), inputValue,
          selectedCurrencyTo.getName(), selectedCurrencyTo.getSymbol(), convertedValue));
    } else {
      System.out.println("Failed to retrieve currency data.");
    }
  }
}
