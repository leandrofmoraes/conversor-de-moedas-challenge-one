package com.challenge.one.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationHelper {
  private static final ResourceBundle messages;

  static {
    Locale systemLocale = Locale.getDefault();
    // Locale systemLocale = Locale.US;
    // Locale systemLocale = Locale.of("es", "AR");
    messages = ResourceBundle.getBundle("MessagesBundle", systemLocale);
  }

  public static ResourceBundle getMessages() {
    return messages;
  }
}
