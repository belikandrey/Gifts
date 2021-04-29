package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractGiftDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Class that interacts with the database
 *
 * @version 1.0
 * @author Andrey Belik
 * @see com.epam.esm.dao.AbstractGiftDAO
 * @see com.epam.esm.dao.UserDAO
 */
@Repository
public class UserDAOImpl extends AbstractGiftDAO<User> implements UserDAO {
  /** Instantiates a new User dao. */
  public UserDAOImpl() {
    setClazz(User.class);
  }
}
