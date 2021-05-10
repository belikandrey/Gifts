package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.hateoas.HateoasResolver;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Order controller. */
@RestController
@RequestMapping("/orders")
public class OrderController {

  /** The Order service. */
  private final OrderService orderService;
  /** The Hateoas resolver. */
  private final HateoasResolver hateoasResolver;

  /**
   * Instantiates a new Order controller.
   *
   * @param orderService the order service
   * @param hateoasResolver the hateoas resolver
   */
  @Autowired
  public OrderController(OrderService orderService, HateoasResolver hateoasResolver) {
    this.orderService = orderService;
    this.hateoasResolver = hateoasResolver;
  }

  /**
   * Create order response entity.
   *
   * @param order the order
   * @return the response entity
   */
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> createOrder(@RequestBody OrderDTO order) {
    OrderDTO orderFromDb = orderService.create(order);
    hateoasResolver.addLinksForOrder(orderFromDb, order.getUserId());
    return new ResponseEntity<>(orderFromDb, HttpStatus.OK);
  }
}
