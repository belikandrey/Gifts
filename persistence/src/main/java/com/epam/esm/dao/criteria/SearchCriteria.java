package com.epam.esm.dao.criteria;

/**
 * Basic interface of criteria for creating search query
 *
 * @version 1.0
 * @author Andery Belik
 */
public interface SearchCriteria {
  /**
   * Method for create and return query
   *
   * @return string query
   */
  String getQuery();
}
