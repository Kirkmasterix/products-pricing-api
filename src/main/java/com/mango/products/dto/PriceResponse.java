package com.mango.products.dto;

import java.time.LocalDate;

public record PriceResponse(
        Long id,
        Double value,
        LocalDate initDate,
        LocalDate endDate,
        Long productId
) {}
