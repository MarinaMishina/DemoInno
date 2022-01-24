package org.inno.shop.service;

import org.inno.shop.entity.Orders;
import org.inno.shop.entity.Products;
import org.inno.shop.exceptions.ResourceNotFoundException;
import org.inno.shop.repository.CustomerRepository;
import org.inno.shop.repository.OrderRepository;
import org.inno.shop.ui.response.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository repo;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductsService productsService;

    @Autowired
    OrderProductsService orderProductsService;


    public List<Orders> getAllOrders() {
        return repo.findAll();
    }

    public Orders getOrderByNumber(String orderNumber) {
        Orders orderEntity = repo.findOrderByNumber(orderNumber);
        if (orderEntity == null) throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        return orderEntity;
    }

    public List<Orders> getByCustomerEmail(String email) {
        if (customerRepository.findByEmail(email) == null)
            throw new ResourceNotFoundException("Not found Customer with email = " + email);

        List<Orders> ordersList = repo.findByCustomerEmail(email);
        if (ordersList.isEmpty()) throw new ResourceNotFoundException(ErrorMessages.NO_RECORDS_FOUND.getErrorMessage());
        return ordersList;
    }

   public Orders createOrder(String customerStrId, Orders order) {
       if (customerRepository.findByCustomerStrId(customerStrId) == null)
           throw new ResourceNotFoundException("Not found Customer with id = " + customerStrId);
       if (repo.findOrderByNumber(order.getNumber()) != null)
           throw new ResourceNotFoundException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
       return repo.save(order);
   }

    public void createOrderProducts(Orders currentOrder) {
        Products product1 = productsService.getProductByScu("501");
        Products product2 = productsService.getProductByScu("500");
        Products product3 = productsService.getProductByScu("503");
        List<Products> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        for (Products p : products) {
            orderProductsService.addProductToOrder(currentOrder, p);
        }
    }

    public Orders updateOrder(String orderNumber, Orders order) {
        Orders orderEntity = repo.findOrderByNumber(orderNumber);
        if (orderEntity == null)
            throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        orderEntity.setDate(order.getDate());
        orderEntity.setAmount(order.getAmount());
        return repo.save(orderEntity);
    }

    public void deleteOrder(String orderNumber) {
        Orders orderEntity = repo.findOrderByNumber(orderNumber);
        if (orderEntity == null) throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        repo.delete(orderEntity);
    }

    public void deleteAllOrdersByCustomer(String customerStrId) {
        if (customerRepository.findByCustomerStrId(customerStrId) == null)
            throw new ResourceNotFoundException("Not found Customer with id = " + customerStrId);
        List<Orders> ordersList = repo.findByCustomer_CustomerStrId(customerStrId);
        if (ordersList.isEmpty()) throw new ResourceNotFoundException(ErrorMessages.NO_RECORDS_FOUND.getErrorMessage());
        repo.deleteAll(ordersList);
    }

}
