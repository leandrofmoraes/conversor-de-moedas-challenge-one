package com.challenge.one.model;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyModel {
  private int id;
  private String code;
  private String name;
  private String symbol;
  private String country;
  private BigDecimal value;

  public CurrencyModel(int id, String code, BigDecimal value) {
    this.id = id;
    this.code = code;
    this.value = value;
    this.name = resolveName(code);
    this.symbol = resolveSymbol(code);
  }

  private String resolveName(String code) {
    try {
      return Currency.getInstance(code).getDisplayName();
    } catch (IllegalArgumentException e) {
      return code;
    }
  }

  private String resolveSymbol(String code) {
    try {
      return Currency.getInstance(code).getSymbol();
    } catch (IllegalArgumentException e) {
      return code;
    }
  }

  public int getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getCountry() {
    return country;
  }

  public BigDecimal getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "[ (" + code + ") " + name + " - " + symbol + " " + value + " ]";
  }
}
