package ru.clevertec.check.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckTest {
    private Check check;
    private Map<Product, Integer> products;

    @BeforeEach
    void setUp() {
        check = new Check();
        products = new HashMap<>();
    }

    @Test
    void testCalculateTotal_NoDiscount() {
        Product product1 = new Product(1, "Product 1", new BigDecimal("10.00"), 5, false);
        Product product2 = new Product(2, "Product 2", new BigDecimal("20.00"), 5, false);

        products.put(product1, 2);
        products.put(product2, 3);

        check.setProducts(products);
        check.setTotalAmount(0);
        check.setTotalDiscount(0);

        check.calculateTotal();

        assertEquals(new BigDecimal("80.0"), BigDecimal.valueOf(check.getTotalAmount()));
        assertEquals(0, check.getTotalDiscount());
    }

    @Test
    void testCalculateTotalWithDiscount() {
        Product product1 = new Product(1, "Product 1", new BigDecimal("10.00"), 5, true);
        Product product2 = new Product(2, "Product 2", new BigDecimal("20.00"), 5, false);

        products.put(product1, 2);
        products.put(product2, 2);

        check.setDiscountCard(new DiscountCard(1L, 1111, (short) 3));
        check.setProducts(products);
        check.calculateTotal();

        assertEquals(new BigDecimal("60.0"), BigDecimal.valueOf(check.getTotalAmount()));
        assertEquals(20 * 0.03 * 2 + 10 * 0.03 * 2, check.getTotalDiscount());
    }

    @Test
    void testCalculateTotalWithDiscountCardAndWholesaleProduct() {
        DiscountCard discountCard = new DiscountCard(1L, 1111, (short) 3);
        Product product1 = new Product(1, "Product 1", new BigDecimal("10.00"), 5, true);
        Product product2 = new Product(2, "Product 2", new BigDecimal("20.00"), 5, false);

        products.put(product1, 5);
        products.put(product2, 3);

        check.setProducts(products);
        check.setDiscountCard(discountCard);

        check.calculateTotal();

        assertEquals(new BigDecimal("110.0"), BigDecimal.valueOf(check.getTotalAmount()));
        assertEquals(0.1 * 10 * 5 + 0.03 * 20 * 3, check.getTotalDiscount());
    }
}