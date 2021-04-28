package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractGiftDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class OrderDAOImpl extends AbstractGiftDAO<Order> implements OrderDAO {

  public OrderDAOImpl() {
    setClazz(Order.class);
  }

  private static final String FIND_BY_USER_ID = "from Order where user_id=:user_id";

  @Override
  public List<Order> findAllByUserId(BigInteger id, Pageable pageable) {
    return getEntityManager()
        .createQuery(FIND_BY_USER_ID, Order.class)
        .setParameter("user_id", id)
        .setFirstResult((pageable.getPage() - 1) * pageable.getSize())
        .setMaxResults(pageable.getSize())
        .getResultList();
  }

  @Override
  public List<Order> findAllByUserId(BigInteger id) {
    return getEntityManager()
        .createQuery(FIND_BY_USER_ID, Order.class)
        .setParameter("user_id", id)
        .getResultList();
  }
}
