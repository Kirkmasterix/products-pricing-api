package com.mango.products.service.impl;

import com.mango.products.domain.Product;
import com.mango.products.dto.CreatePriceRequest;
import com.mango.products.dto.CreateProductRequest;
import com.mango.products.mapper.ProductMapper;
import com.mango.products.repository.ProductRepository;
import com.mango.products.service.IPriceService;
import com.mango.products.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mango.products.exception.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final IPriceService priceService;

    @Override
    public Product createProduct(CreateProductRequest request) {

        Product product = ProductMapper.toEntity(request);
        product = productRepository.save(product);

        // crear precio inicial automáticamente
        priceService.addPrice(new CreatePriceRequest(
                request.initialPrice(),
                request.startDate(),
                product.getId()
        ));

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product getProductWithPrices(Long id) {
        return productRepository.findByIdWithPrices(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
