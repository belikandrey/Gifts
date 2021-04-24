package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.ValidatorException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public Order findById(BigInteger id) {
        final Optional<Order> orderOptional = orderDAO.findById(id);
        if(orderOptional.isEmpty()){
            throw new EntityNotFoundException("Order with id : "+id+" not found", Order.class);
        }
        return orderOptional.get();
    }

    @Override
    public Order add(Order order) throws ValidatorException {
        return orderDAO.add(order);
    }

    @Override
    public void delete(BigInteger id) {
        orderDAO.delete(id);
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }
}
