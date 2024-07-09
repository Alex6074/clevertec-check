package ru.clevertec.check.core;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DiscountCard {
    private long id;
    private int number;
    private short discountAmount;
}
