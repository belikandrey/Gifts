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
          + " certificate.create_date, certificate.last_update_date FROM gifts.certificate"
          + " LEFT JOIN gifts.certificate_tag ON certificate.id = certificate_tag.certificate_id"
          + " LEFT JOIN gifts.tag ON certificate_tag.tag_id = tag.id ";
  private static final String SQL_SET_TAG_NAME = " tag.name = '";
  private static final String SQL_SET_NAME = " certificate.name LIKE '%";
  private static final String SQL_SET_DESCRIPTION = " certificate.description LIKE '%";
  private static final String SQL_SET_SORT_BY_NAME = " ORDER BY certificate.name ";
  private static final String SQL_SET_SORT_BY_DATE = " ORDER BY certificate.create_date ";

  private StringBuilder stringBuilder;
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
    final Separator separator = getSeparator();
    stringBuilder.append(separator.getValue() + SQL_SET_TAG_NAME + tagName + "' ");
    return this;
  }

  /**
   * Method that add certificate name param in query
   *
   * @param name certificate name to find by
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setName(String name) {
    final Separator separator = getSeparator();
    stringBuilder.append(separator.getValue() + SQL_SET_NAME + name + "%' ");
    return this;
  }

  /**
   * Method that add description params in query
   *
   * @param description description to find by
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setDescription(String description) {
    final Separator separator = getSeparator();
    stringBuilder.append(separator.getValue() + SQL_SET_DESCRIPTION + description + "%' ");
    return this;
  }

  /**
   * Method that add type of sort by name in query
   *
   * @param typeOfSort type of sort(asc, desc)
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setSortByName(String typeOfSort) {
    stringBuilder.append(SQL_SET_SORT_BY_NAME + typeOfSort.toUpperCase());
    return this;
  }

  /**
   * Method that add type of sort by date in query
   *
   * @param typeOfSort type of sort(asc, desc)
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setSortByDate(String typeOfSort) {
    stringBuilder.append(SQL_SET_SORT_BY_DATE + typeOfSort.toUpperCase());
    return this;
  }

  private final Separator getSeparator() {
    final Separator separator = Separator.getSeparator(isComposite);
    isComposite = true;
    return separator;
  }
}
