package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigInteger;
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
        return entityManager.createQuery("from Order").getResultList();
    }
}
