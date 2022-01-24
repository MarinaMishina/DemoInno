package org.inno.shop.repository;

import org.inno.shop.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Products, Long> {

    Products findBySku (String scu);

}
