package com.epam.esm.service;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.UserDTO;

import java.math.BigInteger;
import java.util.List;

/** The interface User service. */
public interface UserService extends EntityService<UserDTO, BigInteger> {
  /**
   * Find all list.
   *
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the list of {@link UserDTO}
   */
  List<UserDTO> findAll(PaginationSetting paginationSetting);
}
