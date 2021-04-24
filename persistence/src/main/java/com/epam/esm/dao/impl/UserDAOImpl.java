package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

  private EntityManager entityManager;

  @Autowired
  public UserDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Optional<User> findById(BigInteger id) {
    return Optional.ofNullable(entityManager.find(User.class, id));
  }

  @Override
  public List<User> findAll(Pageable pageable) {
    return entityManager
        .createQuery("from User", User.class)
        .setFirstResult((pageable.getPage() - 1) * pageable.getSize())
        .setMaxResults(pageable.getSize())
        .getResultList();
  }

  @Override
  public User add(User user) {
    throw new UnsupportedOperationException();
  }

  @Override
  public User update(BigInteger id, User user) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean delete(BigInteger id) {
    throw new UnsupportedOperationException();
  }
}
