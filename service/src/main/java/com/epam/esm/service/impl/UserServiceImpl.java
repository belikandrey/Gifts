package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User findById(BigInteger id) {
        final Optional<User> userOptional = userDAO.findById(id);
        if(userOptional.isEmpty()){
            throw new EntityNotFoundException("User with id : "+id+" not found", User.class);
        }
        return userOptional.get();
    }

    @Override
    public User add(User user) throws ValidatorException {
        return userDAO.add(user);
    }

    @Override
    public void delete(BigInteger id) {
        userDAO.delete(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }
}
