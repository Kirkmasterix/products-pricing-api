package com.mango.products.service.impl;

import com.mango.products.domain.Price;
import com.mango.products.domain.Product;
import com.mango.products.dto.CreatePriceRequest;
import com.mango.products.mapper.PriceMapper;
import com.mango.products.repository.PriceRepository;
import com.mango.products.repository.ProductRepository;
import com.mango.products.service.IPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PriceService implements IPriceService {

    private final PriceRepository priceRepository;

    private final ProductRepository productRepository;

    @Override
    public Price addPrice(CreatePriceRequest request) {

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // buscar precio activo actual
        Price current = priceRepository
                .findFirstByProductIdAndEndDateIsNull(product.getId())
                .orElse(null);

        // validación
        if (current != null && request.initDate().isBefore(current.getInitDate())) {
            throw new RuntimeException("New price date cannot be before current price");
        }

        // cerrar el anterior
        if (current != null) {
            current.setEndDate(request.initDate().minusDays(1));
            priceRepository.save(current);
        }

        // crear nuevo precio activo
        Price newPrice = PriceMapper.toEntity(request, product);
        newPrice.setEndDate(null);

        return priceRepository.save(newPrice);
    }

    @Override
    public Price getPriceForDate(Long productId, LocalDate date) {
        return priceRepository.findActivePrice(productId, date)
                .orElseThrow(() -> new RuntimeException("No price for date: " + date));
    }

}