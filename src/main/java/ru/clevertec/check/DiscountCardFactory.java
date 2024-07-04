package ru.clevertec.check;

import java.util.Map;

public class DiscountCardFactory {
    private Map<Integer, DiscountCard> discountCards;

    public DiscountCardFactory() {
        CsvReaderUtil reader = new CsvReaderUtil();
        discountCards = reader.readDiscountCards("./src/main/resources/discountCards.csv");
    }

    public DiscountCard getDiscountCard(int id) {
        return discountCards.get(id);
    }
}
