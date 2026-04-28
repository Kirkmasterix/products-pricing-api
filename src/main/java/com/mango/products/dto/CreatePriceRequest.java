package com.mango.products.dto;

import java.time.LocalDate;

public record CreatePriceRequest(
        Double value,
        LocalDate initDate,
        LocalDate endDate,
        Long productId
) {}