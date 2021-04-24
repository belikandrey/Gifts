package com.epam.esm.search;

import org.springframework.stereotype.Component;

/**
 * Query builder for search by params
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Component
public class CertificateSearchQueryBuilder {
  private static final String SQL_BASIC_QUERY =
      "SELECT DISTINCT certificate.id, certificate.name, certificate.description, "
          + "certificate.price, certificate.duration,"
          + " certificate.create_date, certificate.last_update_date FROM certificate"
          + " LEFT JOIN certificate_tag ON certificate.id = certificate_tag.certificate_id"
          + " LEFT JOIN tag ON certificate_tag.tag_id = tag.id ";
  private static final String SQL_SET_TAG_NAME = " tag.name = '";
  private static final String SQL_SET_NAME = " certificate.name LIKE '%";
  private static final String SQL_SET_DESCRIPTION = " certificate.description LIKE '%";
  private static final String SQL_SET_SORT_BY_NAME = " ORDER BY certificate.name ";
  private static final String SQL_SET_SORT_BY_DATE = " ORDER BY certificate.create_date ";

  private final StringBuilder stringBuilder;
  private boolean isComposite;

  /** Default constructor */
  public CertificateSearchQueryBuilder() {
    stringBuilder = new StringBuilder(SQL_BASIC_QUERY);
  }

  /**
   * Method that finish building query
   *
   * @return query string
   */
  public String build() {
    return stringBuilder.toString();
  }

  /**
   * Method that add tag name param in query
   *
   * @param tagName name of tag to find by
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setTagName(String tagName) {
    final SearchSeparator separator = getSeparator();
    stringBuilder
        .append(separator.getValue())
        .append(SQL_SET_TAG_NAME)
        .append(tagName)
        .append("' ");
    return this;
  }

  /**
   * Method that add certificate name param in query
   *
   * @param name certificate name to find by
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setName(String name) {
    final SearchSeparator separator = getSeparator();
    stringBuilder.append(separator.getValue()).append(SQL_SET_NAME).append(name).append("%' ");
    return this;
  }

  /**
   * Method that add description params in query
   *
   * @param description description to find by
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setDescription(String description) {
    final SearchSeparator separator = getSeparator();
    stringBuilder
        .append(separator.getValue())
        .append(SQL_SET_DESCRIPTION)
        .append(description)
        .append("%' ");
    return this;
  }

  /**
   * Method that add type of sort by name in query
   *
   * @param typeOfSort type of sort(asc, desc)
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setSortByName(String typeOfSort) {
    stringBuilder.append(SQL_SET_SORT_BY_NAME).append(typeOfSort.toUpperCase());
    return this;
  }

  /**
   * Method that add type of sort by date in query
   *
   * @param typeOfSort type of sort(asc, desc)
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setSortByDate(String typeOfSort) {
    stringBuilder.append(SQL_SET_SORT_BY_DATE).append(typeOfSort.toUpperCase());
    return this;
  }

  /**
   * Get search separator method
   *
   * @return {@link SearchSeparator} entity
   */
  private SearchSeparator getSeparator() {
    final SearchSeparator separator = SearchSeparator.getSeparator(isComposite);
    isComposite = true;
    return separator;
  }
}
