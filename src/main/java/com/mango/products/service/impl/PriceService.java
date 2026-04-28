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
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService implements IPriceService {

    private final PriceRepository priceRepository;

    private final ProductRepository productRepository;

    @Override
    public Price addPrice(CreatePriceRequest request) {

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Price price = PriceMapper.toEntity(request, product);

        validateNoOverlapping(price);

        return priceRepository.save(price);
    }

    @Override
    public Price getPriceForDate(Long productId, LocalDate date) {
        return priceRepository
                .findByProductIdAndInitDateLessThanEqualAndEndDateGreaterThanEqual(
                        productId, date, date
                )
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No price for date: " + date));
    }

    private void validateNoOverlapping(Price newPrice) {
        List<Price> existing = priceRepository.findByProductId(newPrice.getProduct().getId());

        for (Price p : existing) {
            boolean overlaps =
                    !(newPrice.getEndDate().isBefore(p.getInitDate()) ||
                            newPrice.getInitDate().isAfter(p.getEndDate()));

            if (overlaps) {
                throw new RuntimeException("Price range overlaps with existing price");
            }
        }
    }
}