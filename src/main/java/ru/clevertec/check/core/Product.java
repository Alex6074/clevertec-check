package ru.clevertec.check.core;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    private long id;
    private String description;
    private BigDecimal price;
    private int quantity;
    private boolean isWholesale;
}
