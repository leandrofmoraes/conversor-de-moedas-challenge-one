package com.challenge.one;

import com.challenge.one.dto.CurrencyDTO;
import com.challenge.one.service.CurrencyService;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {

    CurrencyService currencyService = new CurrencyService();
    CurrencyDTO currencyDTO = currencyService.getCurrencyData();
    if (currencyDTO != null) {
      System.out.println("Last Update: " + currencyDTO.timeLastUpdateUtc());
      currencyService.getCurrencyList().forEach(System.out::println);
    } else {
      System.out.println("Failed to retrieve currency data.");
    }
  }
}
