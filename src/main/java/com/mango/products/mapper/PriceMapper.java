package com.mango.products.mapper;

import com.mango.products.domain.Price;
import com.mango.products.domain.Product;
import com.mango.products.dto.CreatePriceRequest;
import com.mango.products.dto.PriceResponse;

public class PriceMapper {

    public static Price toEntity(CreatePriceRequest request, Product product) {
        Price price = new Price();
        price.setValue(request.value());
        price.setInitDate(request.initDate());
        price.setEndDate(request.endDate());
        price.setProduct(product);
        return price;
    }

    public static PriceResponse toResponse(Price price) {
        return new PriceResponse(
                price.getId(),
                price.getValue(),
                price.getInitDate(),
                price.getEndDate(),
                price.getProduct().getId()
        );
    }
}
