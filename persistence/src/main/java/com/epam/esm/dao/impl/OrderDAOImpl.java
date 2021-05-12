package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractGiftDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.pagination.PaginationSetting;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Class that interacts with the database
 *
 * @version 1.0
 * @author Andrey Belik
 * @see com.epam.esm.dao.AbstractGiftDAO
 * @see com.epam.esm.dao.OrderDAO
 */
@Repository
public class OrderDAOImpl extends AbstractGiftDAO<Order> implements OrderDAO {

  /** Instantiates a new Order dao. */
  public OrderDAOImpl() {
    setClazz(Order.class);
  }

  /** The constant FIND_BY_USER_ID. */
  private static final String FIND_BY_USER_ID = "from Order where user_id=:user_id";

  /**
   * Find all by user id list.
   *
   * @param id the id {@link BigInteger}
   * @param paginationSetting the pagination setting - {@link PaginationSetting}
   * @return the list
   */
  @Override
  public List<Order> findAllByUserId(BigInteger id, PaginationSetting paginationSetting) {
    return getEntityManager()
        .createQuery(FIND_BY_USER_ID, Order.class)
        .setParameter("user_id", id)
        .setFirstResult((paginationSetting.getPage() - 1) * paginationSetting.getSize())
        .setMaxResults(paginationSetting.getSize())
        .getResultList();
  }

  /**
   * Save order.
   *
   * @param entity the {@link Order}
   * @return the {@link Order}
   */
  @Override
  public Order save(Order entity) {
    getEntityManager().persist(entity);
    return entity;
  }
}
