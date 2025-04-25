package com.challenge.one.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.challenge.one.client.CurrencyApiClient;
import com.challenge.one.dto.CurrencyDTO;
import com.challenge.one.model.CurrencyModel;
import com.challenge.one.repository.CurrencyRepository;
import com.google.gson.Gson;

public class CurrencyService {

  private final CurrencyRepository currencyRepository;

  public CurrencyService() {
    CurrencyDTO currencyDTO = getCurrencyDataFromAPI();
    this.currencyRepository = new CurrencyRepository(currencyDTO);
  }

  private CurrencyDTO getCurrencyDataFromAPI() {
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

  public String getLastCurrencyQuoteUpdate() {
    return currencyRepository.getLastCurrencyQuoteUpdate();
  }

  public List<CurrencyModel> getList() {
    return currencyRepository.getAllCurrencies();
  }

  public CurrencyModel getCurrencyByCode(String code) {
    return currencyRepository.getCurrencyByCode(code);
  }

  public CurrencyModel getCurrencyById(int id) {
    return currencyRepository.getCurrencyById(id);
  }

  public BigDecimal convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount) {
    CurrencyModel from = getCurrencyByCode(fromCurrency);
    CurrencyModel to = getCurrencyByCode(toCurrency);

    if (from == null || to == null) {
      throw new IllegalArgumentException("Invalid currency code");
    }

    BigDecimal conversionRate = to.getValue().divide(from.getValue(), 2, RoundingMode.HALF_UP);
    return amount.multiply(conversionRate);
  }
}
