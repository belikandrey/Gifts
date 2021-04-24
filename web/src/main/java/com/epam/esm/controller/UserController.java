package com.epam.esm.controller;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
  private UserService userService;
  private OrderService orderService;

  @Autowired
  public UserController(UserService userService, OrderService orderService) {
    this.userService = userService;
    this.orderService = orderService;
  }

  @GetMapping()
  public ResponseEntity<List<User>> findAll(
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
    Pageable pageable = new Pageable(page, size);
    final List<User> users = userService.findAll(pageable);
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findUserById(@PathVariable("id") BigInteger userId) {
    final User user = userService.findById(userId);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/{id}/orders")
  public ResponseEntity<?> findAllUserOrders(
      @PathVariable("id") BigInteger id,
      @RequestParam(name = "page", defaultValue = "1", required = false) int page,
      @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
    Pageable pageable = new Pageable(size, page);
    final List<Order> orders = orderService.findAllByUserId(id, pageable);
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @PostMapping("/{id}/orders")
  public ResponseEntity<?> createOrder(
      @PathVariable BigInteger id, @RequestBody List<CertificateDTO> certificates) {
    Order order = orderService.create(id, certificates);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  // TODO
  @GetMapping("/{id}/tags")
  public ResponseEntity<?> findMostPopularTag(@PathVariable BigInteger id) {
    return null;
  }

  // TODO
  @GetMapping("/{user_id}/orders/{order_id}")
  public ResponseEntity<?> findUserOrderById(
      @PathVariable("user_id") BigInteger userId, @PathVariable("order_id") BigInteger orderId) {
    Order order = orderService.findByIdAndUserId(orderId, userId);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }
}
