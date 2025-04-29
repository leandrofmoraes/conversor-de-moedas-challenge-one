package com.challenge.one.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHelper {
  private static final ResourceBundle messages;
  private static Locale systemLocale;

  static {
    // Locale systemLocale = Locale.US;
    // Locale systemLocale = Locale.of("es", "AR");
    systemLocale = Locale.getDefault();
    messages = ResourceBundle.getBundle("MessagesBundle", systemLocale);
  }

  public static ResourceBundle getMessages() {
    return messages;
  }

  public static Locale getLocale() {
    return systemLocale;
  }
}
