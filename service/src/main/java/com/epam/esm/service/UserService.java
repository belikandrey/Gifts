package com.epam.esm.service;

import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.UserDTO;

import java.math.BigInteger;
import java.util.List;

public interface UserService extends EntityService<UserDTO, BigInteger> {
  List<UserDTO> findAll(PaginationSetting paginationSetting);
}
