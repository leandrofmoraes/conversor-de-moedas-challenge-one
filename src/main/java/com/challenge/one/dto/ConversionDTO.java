package com.challenge.one.dto;

import java.math.BigDecimal;

public record ConversionDTO(
    String timestamp,
    String fromCurrency,
    String toCurrency,
    BigDecimal amount,
    BigDecimal result) {
}
