package com.epam.esm.dao;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.User;

import java.math.BigInteger;
import java.util.List;

public interface UserDAO extends AbstractDAO<User, BigInteger>{
    List<User> findAll(Pageable pageable);
}
