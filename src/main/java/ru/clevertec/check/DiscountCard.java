package ru.clevertec.check;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountCard {
    private long id;
    private int number;
    private short discountAmount;
}
