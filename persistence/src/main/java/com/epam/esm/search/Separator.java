package com.epam.esm.search;

public enum Separator {
  AND("AND"),
  WHERE("WHERE");

  private String separator;

  Separator(String separator) {
    this.separator = separator;
  }

  public static Separator getSeparator(boolean isComposite) {
    return isComposite ? AND : WHERE;
  }

  public String getValue() {
    return separator;
  }
}
