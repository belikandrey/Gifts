package com.epam.esm.search;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Query builder for search by params
 *
 * @author Andrey Belik
 * @version 1.0
 */
@Component
public class CertificateSearchQueryBuilder {
  /** The constant SQL_BASIC_QUERY. */
  private static final String SQL_BASIC_QUERY =
      "SELECT DISTINCT certificate.id, certificate.name, certificate.is_enabled, certificate.description, "
          + "certificate.price, certificate.duration,"
          + " certificate.create_date, certificate.last_update_date FROM certificate"
          + " LEFT JOIN certificate_tag ON certificate.id = certificate_tag.certificate_id"
          + " LEFT JOIN tag ON certificate_tag.tag_id = tag.id ";

  /** The constant SQL_SET_TAG_NAME. */
  private static final String SQL_SET_TAG_NAME = " tag.name = '";

  /** The constant SQL_SET_NAME. */
  private static final String SQL_SET_NAME = " certificate.name LIKE '%";

  /** The constant SQL_SET_DESCRIPTION. */
  private static final String SQL_SET_DESCRIPTION = " certificate.description LIKE '%";

  /** The constant SQL_SET_SORT_BY_NAME. */
  private static final String SQL_SET_SORT_BY_NAME = " ORDER BY certificate.name ";

  /** The constant SQL_SET_SORT_BY_DATE. */
  private static final String SQL_SET_SORT_BY_DATE = " ORDER BY certificate.create_date ";

  /** The constant SQL_SET_TAG_REPEATED. */
  private static final String SQL_SET_TAG_REPEATED = " tag.name in (";

  /** The constant SQL_GROUP_BY_AND_COUNT. */
  private static final String SQL_GROUP_BY_AND_COUNT =
      ") group by certificate_id having count(*) = ";

  /** The String builder. */
  private final StringBuilder stringBuilder;

  /** The Is composite. */
  private boolean isComposite;

  /** The constant SQL_SET_STATE. */
  private static final String SQL_SET_STATE = " certificate.is_enabled= ";

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
   * Sets state.
   *
   * @param state the state
   * @return the state
   */
  public CertificateSearchQueryBuilder setState(String state) {
    switch (state.toLowerCase()) {
      case "enabled":
        stringBuilder.append(getSeparator()).append(SQL_SET_STATE).append("true ");
        break;
      case "disabled":
        stringBuilder.append(getSeparator()).append(SQL_SET_STATE).append("false");
        break;
      default:
        break;
    }
    return this;
  }

  /**
   * Method that add tag name param in query
   *
   * <p>//@param tagName name of tag to find by
   *
   * @param tagsName the tags name
   * @return {@link CertificateSearchQueryBuilder}
   */
  public CertificateSearchQueryBuilder setTagName(List<String> tagsName) {
    final SearchSeparator separator = getSeparator();
    if (tagsName.size() == 1) {
      stringBuilder
          .append(separator.getValue())
          .append(SQL_SET_TAG_NAME)
          .append(tagsName.get(0))
          .append("' ");
    } else {
      stringBuilder.append(separator.getValue()).append(SQL_SET_TAG_REPEATED);
      for (String tagName : tagsName) {
        stringBuilder.append(" '").append(tagName).append("',");
      }
      stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
      stringBuilder.append(SQL_GROUP_BY_AND_COUNT).append(tagsName.size()).append(" ");
    }
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
