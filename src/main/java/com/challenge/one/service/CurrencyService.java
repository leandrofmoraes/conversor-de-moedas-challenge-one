package com.challenge.one.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.one.client.CurrencyApiClient;
import com.challenge.one.config.ConfigLoader;
import com.challenge.one.dto.ConversionDTO;
import com.challenge.one.dto.CurrencyDTO;
import com.challenge.one.model.CurrencyModel;
import com.challenge.one.repository.CurrencyRepository;
import com.google.gson.Gson;

public class CurrencyService {

  private final CurrencyRepository currencyRepository;
  private static final Logger log = LoggerFactory.getLogger(CurrencyService.class);

  public CurrencyService() {
    CurrencyDTO currencyDTO = getCurrencyDataFromAPI();
    this.currencyRepository = new CurrencyRepository(currencyDTO);
  }

  private CurrencyDTO getCurrencyDataFromAPI() {

    try {
      String apiUrl = ConfigLoader.getApiUrl();
      String currencyData = new CurrencyApiClient(apiUrl).getAPIData();
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

  public CurrencyModel findCurrency(String code) {
    CurrencyModel currency = currencyRepository.getCurrencyByCode(code);
    if (currency != null) {
      return currency;
    } else {
      throw new IllegalArgumentException("Invalid currency code");
    }
  }

  public CurrencyModel findCurrency(int id) {
    CurrencyModel currency = currencyRepository.getCurrencyById(id);
    if (currency != null) {
      return currency;
    } else {
      throw new IllegalArgumentException("Invalid currency id");
    }
  }

  public BigDecimal convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount) {
    CurrencyModel from = findCurrency(fromCurrency);
    CurrencyModel to = findCurrency(toCurrency);

    if (from == null || to == null) {
      throw new IllegalArgumentException("Invalid currency code");
    }

    BigDecimal fromValue = from.getValue();
    BigDecimal toValue = to.getValue();

    BigDecimal conversionRate = toValue.divide(fromValue, 2, RoundingMode.HALF_UP);
    BigDecimal conversionResult = amount.multiply(conversionRate);

    String logMessage = String.format(
        "[CONVERSION] %s → %s | Value: %.2f | Result: %.2f",
        fromValue, toValue, amount, conversionResult);

    // Save conversion history
    new HistoryService().saveHistory(new ConversionDTO(
        LocalDateTime.now().toString(),
        fromCurrency,
        toCurrency,
        amount,
        conversionResult));

    // Log the conversion
    log.info(logMessage);

    return conversionResult;
  }
}
