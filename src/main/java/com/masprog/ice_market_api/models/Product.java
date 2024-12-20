package com.masprog.ice_market_api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "tb_products")
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank
    @Size(min = 2, message = "Product name must contain at least 3 characters")
    private String productName;

    @NotBlank
    @Size(min = 6, message = "Product description must contain at least 6 characters")
    private String productDescription;
    private BigDecimal price;
    private boolean availability;
}
