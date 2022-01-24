package org.inno.shop.repository;

import org.inno.shop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail (String email);
    Customer findByCustomerStrId (String strId);

}
