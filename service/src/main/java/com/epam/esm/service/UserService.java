package com.epam.esm.service;

import com.epam.esm.entity.User;

import java.math.BigInteger;
import java.util.List;

public interface UserService extends EntityService<User, BigInteger>{
    List<User> findAll();
}
