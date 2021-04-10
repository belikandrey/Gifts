package com.epam.esm.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchQueryBuilderTest {

  private CertificateSearchQueryBuilder queryBuilder;
  private static final String SQL_SET_TAG_NAME = " tag.name = '";
  private static final String SQL_SET_NAME = " certificate.name LIKE '%";
  private static final String SQL_SET_DESCRIPTION = " certificate.description LIKE '%";
  private static final String SQL_SET_SORT_BY_NAME = " ORDER BY certificate.name ";
  private static final String SQL_SET_SORT_BY_DATE = " ORDER BY certificate.create_date ";
  private static final String TAG_NAME = "#cool";
  private static final String NAME = "Apple";
  private static final String DESCRIPTION = "Certificate for one Iphone 12 pro";

  @BeforeEach
  public void init() {
    queryBuilder = new CertificateSearchQueryBuilder();
  }

  @Test
  public void setTagNameFirstQueryTest() {
    final String query = queryBuilder.setTagName(TAG_NAME).build();
    assertFalse(query.contains("AND" + SQL_SET_TAG_NAME + TAG_NAME));
    assertTrue(query.contains("WHERE" + SQL_SET_TAG_NAME + TAG_NAME));
  }

  @Test
  public void setTagNameAfterNameTest() {
    final CertificateSearchQueryBuilder builderWithName = queryBuilder.setName(NAME);
    assertTrue(builderWithName.build().contains("WHERE" + SQL_SET_NAME + NAME));
    final String queryWithNameAndTagName = builderWithName.setTagName(TAG_NAME).build();
    assertTrue(queryWithNameAndTagName.contains("AND" + SQL_SET_TAG_NAME + TAG_NAME));
  }

  @Test
  public void setDescriptionTest() {
    final String query = queryBuilder.setDescription(DESCRIPTION).build();
    assertTrue(query.contains("WHERE" + SQL_SET_DESCRIPTION + DESCRIPTION));
  }

  @Test
  public void setSortByDate() {
    final String query = queryBuilder.setSortByDate("asc").build();
    assertTrue(query.contains(SQL_SET_SORT_BY_DATE + "ASC"));
  }

  @Test
  public void setSortByName() {
    final String query = queryBuilder.setSortByName("desc").build();
    assertTrue(query.contains(SQL_SET_SORT_BY_NAME + "DESC"));
  }
}
