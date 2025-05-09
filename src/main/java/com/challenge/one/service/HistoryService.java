package com.challenge.one.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.one.dto.ConversionDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public final class HistoryService {

  private static final int MAX_HISTORY_SIZE = 50;

  private static final Path HISTORY_FILE = Paths.get(
      System.getProperty("user.home"),
      ".conversor-moedas", // pasta oculta no home
      "history.json");

  private final Gson gson = new Gson();
  private final Logger log = LoggerFactory.getLogger(CurrencyService.class);
  private List<ConversionDTO> history;

  public HistoryService() {
    this.history = loadHistory();
  }

  public List<ConversionDTO> getHistory() {
    return new ArrayList<>(history);
  }

  private List<ConversionDTO> loadHistory() {

    if (!Files.exists(HISTORY_FILE))
      return new ArrayList<>();

    try {
      String json = Files.readString(HISTORY_FILE);
      List<ConversionDTO> conversionHistory = gson.fromJson(json, new TypeToken<List<ConversionDTO>>() {
      }.getType());
      return conversionHistory != null ? new ArrayList<>(conversionHistory) : new ArrayList<>();
    } catch (IOException e) {
      log.warn("Error loading history file", e);
      throw new UncheckedIOException("Error loading history file", e);
    }
  }

  public void saveHistory(ConversionDTO conversionDTO) {
    history.add(conversionDTO);

    // If the exceeds the MAX_HISTORY_SIZE, remove the oldest entries.
    if (history.size() > MAX_HISTORY_SIZE) {
      int excess = history.size() - MAX_HISTORY_SIZE;
      history = history.subList(excess, history.size());
    }

    try {
      Files.createDirectories(HISTORY_FILE.getParent());
      String json = gson.toJson(history);
      Files.write(
          HISTORY_FILE,
          json.getBytes(StandardCharsets.UTF_8),
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      log.warn("Error saving history file", e);
      throw new UncheckedIOException("Error saving history file", e);
    }
  }
}
