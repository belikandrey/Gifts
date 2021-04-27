package com.epam.esm.dao;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserDAO {

  Optional<User> findById(BigInteger id);

  List<User> findAll(Pageable pageable);

  User save(User entity);

  User update(User entity);

  void deleteById(BigInteger id);
}
