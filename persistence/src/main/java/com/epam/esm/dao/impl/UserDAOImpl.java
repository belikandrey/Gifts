package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractGiftDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends AbstractGiftDAO<User> implements UserDAO {
  public UserDAOImpl() {
    setClazz(User.class);
  }
}
