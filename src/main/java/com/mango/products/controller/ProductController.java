package com.mango.products.controller;

import com.mango.products.dto.CreateProductRequest;
import com.mango.products.dto.ProductResponse;
import com.mango.products.dto.ProductWithPricesResponse;
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
        return ProductMapper.toResponse(productService.createProduct(request));
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

    @GetMapping("/{id}/prices")
    public ProductWithPricesResponse getPrices(@PathVariable Long id) {
        return ProductMapper.toResponseWithPrices(
                productService.getProductWithPrices(id)
        );
    }
}