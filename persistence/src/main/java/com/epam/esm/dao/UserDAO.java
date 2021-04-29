package com.epam.esm.dao;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/** The interface User dao. */
public interface UserDAO {

  /**
   * Find by id optional.
   *
   * @param id the id
   * @return the {@link Optional} of {@link User}
   */
  Optional<User> findById(BigInteger id);

  /**
   * Find all list.
   *
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the list of {@link User}
   */
  List<User> findAll(PaginationSetting paginationSetting);
}
