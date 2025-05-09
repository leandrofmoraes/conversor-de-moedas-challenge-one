package com.challenge.one.config;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

public class ConfigLoader {
  private static final Properties props = new Properties();

  static {
    // 1) Descobre o profile via system property (ou variável de ambiente)
    String profile = System.getProperty("app.profile",
        System.getenv().getOrDefault("APP_PROFILE", "dev"));

    // 2) Carrega o default (application.properties)
    loadProps("application.properties");

    // 3) Tenta carregar o específico (application-dev.properties ou
    // application-prod.properties)
    boolean loaded = loadProps("application-" + profile + ".properties");
    if (!loaded) {
      // se não existir, opcionalmente logue ou ignore
      System.out.println("[Config] Não encontrou application-" + profile + ".properties, usando defaults");
    }

    // 4) Override por env-vars (ex: API_KEY)
    overrideWithEnvVars();
  }

  /** Tenta carregar um arquivo, retorna true se achou e carregou */
  private static boolean loadProps(String filename) {
    try (InputStream in = ConfigLoader.class.getClassLoader().getResourceAsStream(filename)) {
      if (in != null) {
        props.load(in);
        return true;
      }
    } catch (IOException e) {
      throw new IllegalStateException("Erro ao carregar " + filename, e);
    }
    return false;
  }

  /** Lê env vars que batem com keys e sobrescreve */
  private static void overrideWithEnvVars() {
    for (String key : props.stringPropertyNames()) {
      String envKey = key.toUpperCase().replace('.', '_');
      String val = System.getenv(envKey);
      if (val != null) {
        props.setProperty(key, val);
      }
    }
  }

  /** A chave da API (vem de application-*.properties ou de ENV/API_KEY) */
  private static String getApiKey() {
    return props.getProperty("api.key");
  }

  /**
   * Monta a URL final substituindo o placeholder {0} pelo api.key
   * em application.properties você terá:
   * api.base.url.pattern=https://api.exchangerate.host/v6/{0}/latest
   */
  public static String getApiUrl() {
    String pattern = props.getProperty("api.base.url");
    String key = getApiKey();
    return MessageFormat.format(pattern, key);
  }
}
