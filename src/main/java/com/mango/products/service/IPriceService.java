package com.mango.products.service;

import com.mango.products.domain.Price;
import com.mango.products.dto.CreatePriceRequest;

import java.time.LocalDate;

public interface IPriceService {

    Price addPrice(CreatePriceRequest price);

    Price getPriceForDate(Long productId, LocalDate date);
}
