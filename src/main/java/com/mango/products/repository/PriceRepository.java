package com.mango.products.repository;

import com.mango.products.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductId(Long productId);

    // precio activo en una fecha
    List<Price> findByProductIdAndInitDateLessThanEqualAndEndDateGreaterThanEqual(
            Long productId,
            LocalDate date1,
            LocalDate date2
    );
}
