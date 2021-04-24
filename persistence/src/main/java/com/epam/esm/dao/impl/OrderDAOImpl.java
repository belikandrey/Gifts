package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDAOImpl implements OrderDAO {

  private EntityManager entityManager;

  @Autowired
  public OrderDAOImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Optional<Order> findById(BigInteger id) {
    return Optional.ofNullable(entityManager.find(Order.class, id));
  }

  @Override
  public Order add(Order order) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Order update(BigInteger id, Order order) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean delete(BigInteger id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Order> findAll() {
    return entityManager.createQuery("from Order", Order.class).getResultList();
  }

  @Override
  public List<Order> findAllByUserId(BigInteger id, Pageable pageable) {
    return entityManager
        .createQuery("from Order where user_id=:user_id", Order.class)
        .setParameter("user_id", id)
        .setFirstResult((pageable.getPage() - 1) * pageable.getSize())
        .setMaxResults(pageable.getSize())
        .getResultList();
  }

  @Override
  public Order create(User user, List<Certificate> certificates) {
    BigDecimal price = BigDecimal.ZERO;
    for (Certificate certificate : certificates) {
      price = price.add(certificate.getPrice());
    }
    System.out.println(entityManager);
    Order order = new Order(price, LocalDateTime.now(), certificates, user);
    System.out.println(order);
    order = entityManager.merge(order);
    return order;
  }
}
