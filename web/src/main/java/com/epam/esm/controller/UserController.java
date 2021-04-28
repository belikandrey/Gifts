package com.epam.esm.controller;

import com.epam.esm.dao.pagination.Pageable;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.hateoas.HateoasResolver;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

  private HateoasResolver hateoasResolver;

  @Autowired
  public UserController(UserService userService, OrderService orderService, HateoasResolver hateoasResolver) {
    this.userService = userService;
    this.orderService = orderService;
    this.hateoasResolver = hateoasResolver;
  }

  @GetMapping()
  public CollectionModel<UserDTO> findAll(
      @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
      @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
    Pageable pageable = new Pageable(size, page);
    final List<UserDTO> users = userService.findAll(pageable);
    users.forEach(hateoasResolver::addLinksForUser);
    return hateoasResolver.getModelForUsers(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findUserById(@PathVariable("id") BigInteger userId) {
    final UserDTO user = userService.findById(userId);
    hateoasResolver.addLinksForUser(user);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/{id}/orders")
  public ResponseEntity<?> findAllUserOrders(
      @PathVariable("id") BigInteger id,
      @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
      @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
    Pageable pageable = new Pageable(size, page);
    final List<OrderDTO> orders = orderService.findAllByUserId(id, pageable);
    orders.forEach(p->hateoasResolver.addLinksForOrder(p, id));
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @PostMapping("/{id}/orders")
  public ResponseEntity<?> createOrder(
      @PathVariable BigInteger id, @RequestBody List<CertificateDTO> certificates) {
    OrderDTO order = orderService.create(id, certificates);
    hateoasResolver.addLinksForOrder(order, id);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }

  // TODO
  @GetMapping("/{user_id}/orders/{order_id}")
  public ResponseEntity<?> findUserOrderById(
      @PathVariable("user_id") BigInteger userId, @PathVariable("order_id") BigInteger orderId) {
    OrderDTO order = orderService.findByIdAndUserId(orderId, userId);
    hateoasResolver.addLinksForOrder(order, userId);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }
}
