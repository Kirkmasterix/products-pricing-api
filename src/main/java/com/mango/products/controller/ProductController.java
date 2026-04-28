package com.mango.products.controller;

import com.mango.products.dto.CreateProductRequest;
import com.mango.products.dto.ProductResponse;
import com.mango.products.mapper.ProductMapper;
import com.mango.products.service.IProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse create(@RequestBody CreateProductRequest request) {
        var product = ProductMapper.toEntity(request);
        var saved = productService.createProduct(product);
        return ProductMapper.toResponse(saved);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return productService.getAllProducts()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return ProductMapper.toResponse(productService.getById(id));
    }
}