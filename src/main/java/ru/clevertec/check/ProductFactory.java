package ru.clevertec.check;

import java.util.HashMap;
import java.util.Map;

public class ProductFactory {
    private Map<Integer, Product> products;

    public ProductFactory(String filePath) {
        CsvReaderUtil reader = new CsvReaderUtil();
        products = reader.readProducts(filePath);
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }
}
