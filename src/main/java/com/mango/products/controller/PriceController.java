package com.mango.products.controller;

import com.mango.products.dto.CreatePriceRequest;
import com.mango.products.dto.PriceResponse;
import com.mango.products.mapper.PriceMapper;
import com.mango.products.service.IPriceService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final IPriceService priceService;

    public PriceController(IPriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    public PriceResponse add(@RequestBody CreatePriceRequest request) {
        return PriceMapper.toResponse(priceService.addPrice(request));
    }

    @GetMapping("/product/{productId}")
    public PriceResponse getByDate(
            @PathVariable Long productId,
            @RequestParam String date
    ) {
        return PriceMapper.toResponse(
                priceService.getPriceForDate(productId, LocalDate.parse(date))
        );
    }
}
