package org.inno.shop.ui.controller;


import org.inno.shop.dto.CustomerDto;
import org.inno.shop.entity.Customer;
import org.inno.shop.service.CustomerService;
import org.inno.shop.ui.request.CustomerRequestModel;
import org.inno.shop.ui.response.CustomerRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("customers")
@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<CustomerRest> showCustomersList() {
        List<CustomerRest> returnValue = new ArrayList<>();
        List<CustomerDto> customers = customerService.getAllCustomers();

        for (CustomerDto dto : customers) {
            CustomerRest customerRest = new CustomerRest();
            BeanUtils.copyProperties(dto, customerRest);
            returnValue.add(customerRest);
        }
        return returnValue;
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CustomerRest getCustomer(@PathVariable String id) {
        CustomerRest returnValue = new CustomerRest();
        CustomerDto customerDto = customerService.getCustomerByStringId(id);
        BeanUtils.copyProperties(customerDto, returnValue);
        return returnValue;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CustomerRest createCustomer(@RequestBody CustomerRequestModel customerDetails) throws Exception {
        CustomerRest returnValue = new CustomerRest();
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customerDetails, customerDto);
        CustomerDto createdCustomer = customerService.createCustomer(customerDto);
        BeanUtils.copyProperties(createdCustomer, returnValue);
        return returnValue;
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public CustomerRest updateCustomer(@PathVariable String id, @RequestBody CustomerRequestModel customerDetails) {
        return null;
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
