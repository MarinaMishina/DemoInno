package org.inno.shop.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.inno.shop.entity.Products;
import org.inno.shop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping (produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Products>> showProductsList() {
        List<Products> products = productsService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "/{productId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Products> getProductById (@PathVariable Long productId) {
        return new ResponseEntity<>(productsService.getProduct(productId), HttpStatus.OK);
    }

    @PutMapping(path = "/{productId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Products> updateProduct (@PathVariable Long productId, @RequestBody Products product) {
        return new ResponseEntity<>(productsService.updateProduct(productId, product), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{productId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Products> deleteProduct (@PathVariable Long productId) {
        productsService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
