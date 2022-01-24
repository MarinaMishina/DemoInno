package org.inno.shop.repository;

import org.inno.shop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository <Orders, Long> {

    Orders findOrderByNumber (String orderNumber);
    List<Orders> findByCustomerEmail (String email);
    List<Orders> findByCustomer_CustomerStrId (String customerStrId);
}
