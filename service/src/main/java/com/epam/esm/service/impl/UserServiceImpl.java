package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private UserDAO userDAO;
  private Converter<User, UserDTO> converter;

  @Autowired
  public UserServiceImpl(UserDAO userDAO, Converter<User, UserDTO> converter) {
    this.userDAO = userDAO;
    this.converter = converter;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDTO findById(BigInteger id) {
    final Optional<User> userOptional = userDAO.findById(id);
    if (userOptional.isEmpty()) {
      throw new EntityNotFoundException("User with id : " + id + " not found", User.class);
    }
    return converter.convertToDto(userOptional.get());
  }

  @Override
  public UserDTO add(UserDTO userDTO) throws ValidatorException {
    final User user = converter.convertToEntity(userDTO);
    return converter.convertToDto(userDAO.save(user));
  }

  @Override
  public void delete(BigInteger id) {
    userDAO.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDTO> findAll(Pageable pageable) {
    return userDAO.findAll(pageable).stream()
        .map(converter::convertToDto)
        .collect(Collectors.toList());
  }
}
