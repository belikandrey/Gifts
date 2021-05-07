package com.epam.esm.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/** The type Translator. */
@Component
public class Translator {

  /** The Message source. */
  private final ResourceBundleMessageSource messageSource;

  /**
   * Instantiates a new Translator.
   *
   * @param messageSource the message source
   */
  @Autowired
  public Translator(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * To locale string.
   *
   * @param messageKey the message key
   * @return the string
   */
  public String toLocale(String messageKey) {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(messageKey, null, locale);
  }
}
