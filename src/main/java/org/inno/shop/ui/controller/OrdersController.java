package org.inno.shop.ui.controller;


import org.inno.shop.dto.CustomerDto;
import org.inno.shop.entity.Customer;
import org.inno.shop.entity.Orders;
import org.inno.shop.exceptions.ResourceNotFoundException;
import org.inno.shop.service.CustomerService;
import org.inno.shop.service.OrderService;
import org.inno.shop.ui.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("orders")
@Controller
public class OrdersController {

    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;


    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Orders>> showAllOrders() {
        List<Orders> orders = orderService.getAllOrders();
        if (orders.isEmpty()) throw new ResourceNotFoundException(ErrorMessages.NO_RECORDS_FOUND.getErrorMessage());
        return new ResponseEntity<>(orders, HttpStatus.OK );
    }

    @GetMapping(path = "/{orderNumber}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Orders> findOrderByNumber(@PathVariable(value = "orderNumber") String orderNumber) {
        Orders order = orderService.getOrderByNumber(orderNumber);
        if (order == null) throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping(path = "/{customerStrId}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})

    public ResponseEntity<Orders> createOrder (@PathVariable(value = "customerStrId") String customerStrId, @RequestBody Orders orderRequest) {
        CustomerDto dto = customerService.getCustomerByStringId(customerStrId);
        if (dto == null) throw new ResourceNotFoundException("Not found Customer with id = " + customerStrId);
        Customer customerEntity = new Customer();
        BeanUtils.copyProperties(dto, customerEntity);
        orderRequest.setDate(now());
        Orders order = orderService.createOrder(customerStrId, orderRequest.setCustomer(customerEntity));
        orderService.createOrderProducts(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{orderNumber}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Orders> updateOrderByNumber(@PathVariable(value = "orderNumber") String orderNumber, @RequestBody Orders order) {

        return new ResponseEntity<>(orderService.updateOrder(orderNumber, order), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{orderNumber}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Orders> deleteOrder(@PathVariable(value = "orderNumber") String orderNumber) {
        orderService.deleteOrder(orderNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{customerStrId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Orders>> deleteAllOrdersByCustomer(@PathVariable(value = "customerStrId") String customerStrId) {
        orderService.deleteAllOrdersByCustomer(customerStrId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
