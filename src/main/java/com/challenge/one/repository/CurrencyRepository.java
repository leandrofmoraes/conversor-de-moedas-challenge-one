package com.challenge.one.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.challenge.one.dto.CurrencyDTO;
import com.challenge.one.model.CurrencyModel;

/**
 * CurrencyRepository is responsible for managing currency data.
 */
public final class CurrencyRepository {

  private final CurrencyDTO currencyDTO;
  private final List<CurrencyModel> currencyList;

  // This class will handle the database operations related to currency data
  // For now, we can leave it empty or add some basic methods
  public CurrencyRepository(CurrencyDTO currencyDTO) {
    this.currencyDTO = currencyDTO;
    this.currencyList = saveCurrencyList();
  }

  private List<CurrencyModel> saveCurrencyList() {
    List<Map.Entry<String, BigDecimal>> entries = new ArrayList<>(currencyDTO.conversionRates().entrySet());

    return IntStream.range(0, entries.size())
        .mapToObj(i -> {
          Map.Entry<String, BigDecimal> entry = entries.get(i);
          return new CurrencyModel(i, entry.getKey(), entry.getValue());
        }).toList();
  }

  public String getLastCurrencyQuoteUpdate() {
    return currencyDTO.timeLastUpdateUtc();
  }

  public List<CurrencyModel> getAllCurrencies() {
    return currencyList;
  }

  public CurrencyModel getCurrencyByCode(String code) {
    return currencyList.stream()
        .filter(currency -> currency.getCode().equalsIgnoreCase(code))
        .findFirst()
        .orElse(null);
  }

  public CurrencyModel getCurrencyById(int id) {
    return currencyList.stream()
        .filter(currency -> currency.getId() == id)
        .findFirst()
        .orElse(null);
  }
}
