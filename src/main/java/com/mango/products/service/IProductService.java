package com.mango.products.service;

import com.mango.products.domain.Product;

import java.util.List;

public interface IProductService {

    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product getById(Long id);
}
