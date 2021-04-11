package com.epam.esm.dao.criteria.impl;

import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.search.CertificateSearchQueryBuilder;

import java.util.Map;

/**
 * Class for creating and returning query for search certificates
 *
 * @version 1.0
 * @author Andrey Belik
 * @see com.epam.esm.dao.criteria.SearchCriteria
 */
public class CertificateSearchCriteria implements SearchCriteria {

  /**
   * Certificate query builder. For building query by params
   *
   * @see CertificateSearchQueryBuilder
   */
  private CertificateSearchQueryBuilder builder;

  /** Params for find by */
  private Map<String, String> params;

  /**
   * Constructor
   *
   * @param params params for searching by
   */
  public CertificateSearchCriteria(Map<String, String> params) {
    this.params = params;
    builder = new CertificateSearchQueryBuilder();
  }

  /**
   * Create and return query for certificate search method
   *
   * @return query for search certificate
   */
  @Override
  public String getQuery() {
    String name = params.get("name");
    final String tagName = params.get("tagName");
    final String description = params.get("description");
    final String sortName = params.get("sortName");
    final String sortDate = params.get("sortDate");
    return getQueryFromBuilder(name, tagName, description, sortName, sortDate);
  }

  /**
   * Create query by {@link CertificateSearchQueryBuilder} and return query after build
   *
   * @param name name of certificate
   * @param tagName name of tag
   * @param description description of certificate
   * @param sortName sort by name param(asc, desc)
   * @param sortDate sort by date param(asc, desc)
   * @return query for search certificate
   */
  private String getQueryFromBuilder(
      String name, String tagName, String description, String sortName, String sortDate) {
    builder = tagName != null && !tagName.isEmpty() ? builder.setTagName(tagName) : builder;
    builder = name != null && !name.isEmpty() ? builder.setName(name) : builder;
    builder =
        description != null && !description.isEmpty()
            ? builder.setDescription(description)
            : builder;
    builder =
        sortName != null
                && !sortName.isEmpty()
                && (sortName.equalsIgnoreCase("asc") || sortName.equalsIgnoreCase("desc"))
            ? builder.setSortByName(sortName)
            : sortDate != null
                    && !sortDate.isEmpty()
                    && (sortDate.equalsIgnoreCase("asc") || sortDate.equalsIgnoreCase("desc"))
                ? builder.setSortByDate(sortDate)
                : builder;
    return builder.build();
  }
}