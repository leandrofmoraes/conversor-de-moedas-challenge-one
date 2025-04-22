package com.challenge.one.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public record CurrencyDTO(
    @SerializedName("base_code") String baseCode,
    @SerializedName("time_last_update_utc") String timeLastUpdateUtc,
    @SerializedName("conversion_rates") Map<String, BigDecimal> conversionRates) {
}
