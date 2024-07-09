package ru.clevertec.check;


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
