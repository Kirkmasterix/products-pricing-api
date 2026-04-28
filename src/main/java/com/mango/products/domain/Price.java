package com.mango.products.domain;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Table(name = "prices")
@Getter
@Setter
@NoArgsConstructor
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double value;

    private LocalDate initDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
