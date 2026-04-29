package com.mango.products.dto;

import java.time.LocalDate;

public record CreateProductRequest(
        String name,
        String description,
        Double initialPrice,
        LocalDate startDate
) {}
