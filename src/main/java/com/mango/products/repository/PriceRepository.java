package com.mango.products.repository;

import com.mango.products.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> findByProductId(Long productId);

    @Query("""
      SELECT p FROM Price p
      WHERE p.product.id = :productId
      AND p.initDate <= :date
      AND (p.endDate IS NULL OR p.endDate >= :date)
    """)
    Optional<Price> findActivePrice(Long productId, LocalDate date);

    Optional<Price> findFirstByProductIdAndEndDateIsNull(Long productId);
}
