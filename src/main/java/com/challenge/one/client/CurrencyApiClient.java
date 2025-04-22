package com.challenge.one.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/*
 * This class is responsible for making HTTP requests to the currency API.
 * It uses Java's HttpClient to send GET requests and retrieve currency data.
 */
public class CurrencyApiClient {

  private final HttpClient httpClient;
  private final String uriString;

  /*
   * Constructor that initializes the HttpClient and sets the default URI for the
   * API.
   */
  public CurrencyApiClient() {
    // Creates a client with default settings
    // HttpClient httpClient = HttpClient.newHttpClient();

    // Creates a client with custom settings
    this.httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();

    // Uses a default URI
    this.uriString = "https://v6.exchangerate-api.com/v6/8fa6e88728197c9cf3098192/latest/USD";
  }

  public CurrencyApiClient(String uri) {
    this.httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();
    this.uriString = uri;
  }

  /*
   * This method sends a GET request to the API and retrieves the currency data.
   * It returns the response body as a String.
   */
  public String getAPIData() throws IOException, InterruptedException {

    HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(uriString))
        .header("accept", "application/json")
        .timeout(Duration.ofSeconds(10))
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();
  }
}
