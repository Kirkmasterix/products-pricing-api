package com.mango.products.repository;

import com.mango.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
     SELECT p FROM Product p
     LEFT JOIN FETCH p.prices pr
     WHERE p.id = :id
     ORDER BY pr.initDate ASC
    """)
    Optional<Product> findByIdWithPrices(@Param("id") Long id);
}
