package com.mango.products.mapper;

import com.mango.products.domain.Product;
import com.mango.products.dto.CreateProductRequest;
import com.mango.products.dto.ProductResponse;

public class ProductMapper {

    public static Product toEntity(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        return product;
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription()
        );
    }
}