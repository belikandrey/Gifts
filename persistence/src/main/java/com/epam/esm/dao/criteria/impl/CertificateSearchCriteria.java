package com.epam.esm.dao.criteria.impl;

import com.epam.esm.dao.criteria.SearchCriteria;
import com.epam.esm.search.CertificateSearchQueryBuilder;

import java.util.Map;

public class CertificateSearchCriteria implements SearchCriteria {

  private CertificateSearchQueryBuilder builder;

  private Map<String, String> params;

  public CertificateSearchCriteria(Map<String, String> params) {
    this.params = params;
    builder = new CertificateSearchQueryBuilder();
  }

  @Override
  public String getQuery() {
    String name = params.get("name");
    final String tagName = params.get("tagName");
    final String description = params.get("description");
    final String sortName = params.get("sortName");
    final String sortDate = params.get("sortDate");
    return getQueryFromBuilder(name, tagName, description, sortName, sortDate);
  }

  private String getQueryFromBuilder(
      String name, String tagName, String description, String sortName, String sortDate) {
    builder = tagName != null && !tagName.isEmpty() ? builder.setTagName(tagName) : builder;
    builder = name != null && !name.isEmpty() ? builder.setName(name) : builder;
    builder =
        description != null && !description.isEmpty()
            ? builder.setDescription(description)
            : builder;
    builder =
        sortName != null && !sortName.isEmpty()
            ? builder.setSortByName(sortName)
            : sortDate != null && !sortDate.isEmpty() ? builder.setSortByDate(sortDate) : builder;
    return builder.build();
  }
}
