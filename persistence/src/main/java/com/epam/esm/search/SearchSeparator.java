package com.epam.esm.search;

public enum SearchSeparator {
  AND("AND"),
  WHERE("WHERE");

  private final String separator;

  SearchSeparator(String separator) {
    this.separator = separator;
  }

  public static SearchSeparator getSeparator(boolean isComposite) {
    return isComposite ? AND : WHERE;
  }

  public String getValue() {
    return separator;
  }
}
