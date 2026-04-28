package com.mango.products.service;

import com.mango.products.domain.Product;
import com.mango.products.dto.CreateProductRequest;

import java.util.List;

public interface IProductService {

    Product createProduct(CreateProductRequest product);

    List<Product> getAllProducts();

    Product getById(Long id);
}
