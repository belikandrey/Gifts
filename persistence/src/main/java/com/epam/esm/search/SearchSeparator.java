package com.epam.esm.search;

/** The enum Search separator. */
public enum SearchSeparator {
  /** And search separator. */
  AND("AND"),
  /** Where search separator. */
  WHERE("WHERE");

  /** The Separator. */
  private final String separator;

  /**
   * Instantiates a new Search separator.
   *
   * @param separator the separator
   */
  SearchSeparator(String separator) {
    this.separator = separator;
  }

  /**
   * Gets separator.
   *
   * @param isComposite the is composite
   * @return the separator
   */
  public static SearchSeparator getSeparator(boolean isComposite) {
    return isComposite ? AND : WHERE;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public String getValue() {
    return separator;
  }
}
