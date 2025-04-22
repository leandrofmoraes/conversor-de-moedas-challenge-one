package com.challenge.one.service;

import java.io.IOException;
import java.util.List;

import com.challenge.one.client.CurrencyApiClient;
import com.challenge.one.dto.CurrencyDTO;
import com.challenge.one.model.CurrencyModel;
import com.google.gson.Gson;

public class CurrencyService {

  public CurrencyDTO getCurrencyData() {

    String uri = "https://v6.exchangerate-api.com/v6/8fa6e88728197c9cf3098192/latest/USD";

    try {
      String currencyData = new CurrencyApiClient(uri).getAPIData();
      CurrencyDTO currencyDTO = new Gson().fromJson(currencyData, CurrencyDTO.class);

      return currencyDTO;

    } catch (IOException | InterruptedException e) {
      System.out.println("Error: " + e.getMessage());
    }

    return null;
  }

  public List<CurrencyModel> getCurrencyList() {
    CurrencyDTO currencyDTO = getCurrencyData();

    return currencyDTO.conversionRates().entrySet().stream()
        .map(currency -> new CurrencyModel(currency.getKey(), currency.getValue()))
        .toList();
  }
}
