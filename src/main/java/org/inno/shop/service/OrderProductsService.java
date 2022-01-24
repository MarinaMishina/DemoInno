package org.inno.shop.service;

import org.inno.shop.entity.Orders;
import org.inno.shop.entity.Products;
import org.inno.shop.repository.OrderRepository;
import org.inno.shop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductsService {

    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    OrderRepository orderRepository;

    public void addProductToOrder (Orders orderId, Products productId) {
        Orders order = orderRepository.findOrderByNumber(orderId.getNumber());
        Products product = productsRepository.getById(productId.getId());
        order.getProducts().add(product);
        orderRepository.save(order);
    }

}
