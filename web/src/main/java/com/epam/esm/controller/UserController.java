package com.epam.esm.controller;

import com.epam.esm.dao.pagination.PaginationSetting;
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

/** The type User controller. */
@RestController
@RequestMapping("/users")
public class UserController {
  /** The User service. */
  private UserService userService;

  /** The Order service. */
  private OrderService orderService;

  /** The Hateoas resolver. */
  private HateoasResolver hateoasResolver;

  /**
   * Instantiates a new User controller.
   *
   * @param userService the user service
   * @param orderService the order service
   * @param hateoasResolver the hateoas resolver
   */
  @Autowired
  public UserController(
      UserService userService, OrderService orderService, HateoasResolver hateoasResolver) {
    this.userService = userService;
    this.orderService = orderService;
    this.hateoasResolver = hateoasResolver;
  }

  /**
   * Find all collection model.
   *
   * @param page the page
   * @param size the size
   * @return the collection model
   */
  @GetMapping()
  public CollectionModel<UserDTO> findAll(
      @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
      @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
    PaginationSetting paginationSetting = PaginationSetting.getInstance(size, page);
    final List<UserDTO> users = userService.findAll(paginationSetting);
    users.forEach(hateoasResolver::addLinksForUser);
    return hateoasResolver.getModelForUsers(users);
  }

  /**
   * Find user by id response entity.
   *
   * @param userId the user id
   * @return the response entity
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> findUserById(@PathVariable("id") BigInteger userId) {
    final UserDTO user = userService.findById(userId);
    hateoasResolver.addLinksForUser(user);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  /**
   * Find all user orders response entity.
   *
   * @param id the id
   * @param page the page
   * @param size the size
   * @return the response entity
   */
  @GetMapping("/{id}/orders")
  public ResponseEntity<?> findAllUserOrders(
      @PathVariable("id") BigInteger id,
      @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
      @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
    PaginationSetting paginationSetting = PaginationSetting.getInstance(size, page);
    final List<OrderDTO> orders = orderService.findAllByUserId(id, paginationSetting);
    orders.forEach(p -> hateoasResolver.addLinksForOrder(p, id));
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  /**
   * Create order response entity.
   *
   * @param id the id
   * @param certificates the certificates
   * @return the response entity
   */
//  @PostMapping("/{id}/orders")
//  public ResponseEntity<?> createOrder(
//      @PathVariable BigInteger id, @RequestBody List<CertificateDTO> certificates) {
//    OrderDTO order = orderService.create(id, certificates);
//    hateoasResolver.addLinksForOrder(order, id);
//    return new ResponseEntity<>(order, HttpStatus.OK);
//  }

  /**
   * Find user order by id response entity.
   *
   * @param userId the user id
   * @param orderId the order id
   * @return the response entity
   */
  @GetMapping("/{user_id}/orders/{order_id}")
  public ResponseEntity<?> findUserOrderById(
      @PathVariable("user_id") BigInteger userId, @PathVariable("order_id") BigInteger orderId) {
    OrderDTO order = orderService.findByIdAndUserId(orderId, userId);
    hateoasResolver.addLinksForOrder(order, userId);
    return new ResponseEntity<>(order, HttpStatus.OK);
  }
}
