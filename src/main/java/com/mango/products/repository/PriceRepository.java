package com.mango.products.repository;

import com.mango.products.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
