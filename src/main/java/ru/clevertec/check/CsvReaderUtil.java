package ru.clevertec.check;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvReaderUtil {
    public Map<Integer, Product> readProducts(String filePath) {
        Map<Integer, Product> products = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                int id = Integer.parseInt(values[0]);
                String name = values[1];
                double price = Double.parseDouble(values[2]);
                int quantity = Integer.parseInt(values[3]);
                boolean isWholesale = Boolean.parseBoolean(values[4]);
                products.put(id, new Product(id, name, price, quantity, isWholesale));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Map<Integer, DiscountCard> readDiscountCards(String filePath) {
        Map<Integer, DiscountCard> discountCards = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                int id = Integer.parseInt(values[0]);
                int number = Integer.parseInt(values[1]);
                int discountAmount = Integer.parseInt(values[2]);
                discountCards.put(number, new DiscountCard(id, number, discountAmount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return discountCards;
    }
}
