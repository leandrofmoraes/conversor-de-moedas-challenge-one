package com.challenge.one;

import java.util.List;

import com.challenge.one.dto.CurrencyDTO;
import com.challenge.one.model.CurrencyModel;
import com.challenge.one.service.CurrencyService;
import com.challenge.one.util.ConsoleHelper;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {

    CurrencyService currencyService = new CurrencyService();
    List<CurrencyModel> currencies = currencyService.getCurrencyList();

    List<String> currencyList = currencies.stream()
        .map(currency -> currency.getCode() + " - " + currency.getName())
        .toList();

    CurrencyDTO currencyDTO = currencyService.getCurrencyData();
    ConsoleHelper.printTitle("Currency Converter");

    if (currencyDTO != null) {
      System.out.println("Last Update: " + currencyDTO.timeLastUpdateUtc());
      ConsoleHelper.printMenuList(currencyList);
      ConsoleHelper.printMessage("Select a currency by entering the corresponding number:");
      int inputIndex = ConsoleHelper.getInput().intValue();
      CurrencyModel selectedCurrency = currencies.get(inputIndex);
      ConsoleHelper.printMessage(
          "Voce selecionouu a moeda: " + selectedCurrency.getName() + " - " + selectedCurrency.getSymbol()
              + selectedCurrency.getValue());
    } else {
      System.out.println("Failed to retrieve currency data.");
    }
  }
}
