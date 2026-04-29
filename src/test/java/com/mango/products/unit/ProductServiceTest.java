package com.mango.products.unit;

import com.mango.products.domain.Product;
import com.mango.products.dto.CreateProductRequest;
import com.mango.products.exception.ProductNotFoundException;
import com.mango.products.repository.ProductRepository;
import com.mango.products.service.IPriceService;
import com.mango.products.service.impl.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private IPriceService priceService;

    @InjectMocks
    private ProductService productService;

    //Crear producto + precio inicial
    @Test
    void shouldCreateProduct_andInitialPrice() {
        CreateProductRequest request = new CreateProductRequest(
                "Product A",
                "desc",
                100.0,
                LocalDate.of(2024, 1, 1)
        );

        Product saved = new Product();
        saved.setId(1L);

        when(productRepository.save(any())).thenReturn(saved);

        Product result = productService.createProduct(request);

        assertNotNull(result);

        verify(productRepository).save(any());
        verify(priceService).addPrice(any());
    }

    // getById OK
    @Test
    void shouldReturnProduct_whenExists() {
        Product product = new Product();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getById(1L);

        assertNotNull(result);
    }

    // getById NOT FOUND
    @Test
    void shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> productService.getById(1L));
    }

    // getProductWithPrices
    @Test
    void shouldReturnProductWithPrices() {
        Product product = new Product();

        when(productRepository.findByIdWithPrices(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.getProductWithPrices(1L);

        assertNotNull(result);
    }
}
