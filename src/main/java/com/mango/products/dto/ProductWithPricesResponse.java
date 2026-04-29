package com.mango.products.dto;

import java.util.List;

public record ProductWithPricesResponse(
        Long id,
        String name,
        String description,
        List<PriceResponse> prices
) {}
