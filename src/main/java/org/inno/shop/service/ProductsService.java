package org.inno.shop.service;

import org.inno.shop.entity.Products;
import org.inno.shop.exceptions.ResourceNotFoundException;
import org.inno.shop.repository.ProductsRepository;
import org.inno.shop.ui.response.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    @Autowired
    ProductsRepository repo;

    public List<Products> getAllProducts() {
        return repo.findAll();
    }

    public Products getProduct (Long id) {

        Products productEntity = repo.findById(id).get();

        if (productEntity == null) {
            throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return productEntity;
    }

    public Products getProductByScu (String productScu) {
        Products productEntity = repo.findBySku(productScu);
        if (productEntity == null) {
            throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return productEntity;
    }

    public Products updateProduct (Long id, Products product) {

        Products productEntity = repo.findById(id).get();
        if (productEntity == null) {
            throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        productEntity.setSku(product.getSku());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        return repo.save(productEntity);
    }

    public void deleteProduct (Long id) {
        Products productEntity = repo.findById(id).get();
        if (productEntity == null) {
            throw new ResourceNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        repo.deleteById(id);
    }

    public List<Products> createProductsList (Products product) {
        List<Products> productsList = new ArrayList<>();
        productsList.add(product);
        return productsList;
    }

}
