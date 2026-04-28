package com.mango.products.service;

import com.mango.products.domain.Price;

import java.time.LocalDate;

public interface IPriceService {

    Price addPrice(Price price);

    Price getPriceForDate(Long productId, LocalDate date);
}
