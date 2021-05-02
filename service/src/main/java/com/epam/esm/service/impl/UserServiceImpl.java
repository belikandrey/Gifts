package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/** The type User service. */
@Service
@Transactional
public class UserServiceImpl implements UserService {

  /** The User dao. */
  private UserDAO userDAO;

  /** The Converter. */
  private Converter<User, UserDTO> converter;

  /**
   * Instantiates a new User service.
   *
   * @param userDAO the {@link UserDAO}
   * @param converter the {@link Converter}
   */
  @Autowired
  public UserServiceImpl(UserDAO userDAO, Converter<User, UserDTO> converter) {
    this.userDAO = userDAO;
    this.converter = converter;
  }

  /**
   * Find by id user dto.
   *
   * @param id the id
   * @return the {@link UserDTO}
   */
  @Override
  @Transactional(readOnly = true)
  public UserDTO findById(BigInteger id) {
    User user =
        userDAO
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException("User with id : " + id + " not found", User.class));
    return converter.convertToDto(user);
  }

  /**
   * Find all.
   *
   * @param paginationSetting the pagination setting {@link PaginationSetting}
   * @return the list of {@link UserDTO}
   */
  @Override
  @Transactional(readOnly = true)
  public List<UserDTO> findAll(PaginationSetting paginationSetting) {
    return userDAO.findAll(paginationSetting).stream()
        .map(converter::convertToDto)
        .collect(Collectors.toList());
  }
}
