package ru.clevertec.check;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Check {
    private Map<Product, Integer> products = new HashMap<>();
    private DiscountCard discountCard;
    private double totalAmount;
    private double totalDiscount;
    private double balanceDebitCard;
    private ProductFactory productFactory;
    private DiscountCardFactory discountCardFactory;

    public Check(String[] args) {
        balanceDebitCard = Double.MIN_VALUE;
        productFactory = new ProductFactory();
        discountCardFactory = new DiscountCardFactory();

        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                int cardId = Integer.parseInt(arg.split("=")[1]);
                discountCard = discountCardFactory.getDiscountCard(cardId);
            } else if (arg.startsWith("balanceDebitCard=")) {
                balanceDebitCard = Double.parseDouble(arg.split("=")[1]);
            } else {
                String[] parts = arg.split("-");
                int productId = Integer.parseInt(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                Product product = productFactory.getProduct(productId);
                products.put(product, products.getOrDefault(product, 0) + quantity);
            }
        }

        if (!products.containsKey(null))
            calculateTotal();
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double discount) {
        this.totalDiscount = discount;
    }

    public double getBalanceDebitCard() {
        return balanceDebitCard;
    }

    public void setBalanceDebitCard(double balanceDebitCard) {
        this.balanceDebitCard = balanceDebitCard;
    }

    public ProductFactory getProductFactory() {
        return productFactory;
    }

    public DiscountCardFactory getDiscountCardFactory() {
        return discountCardFactory;
    }

    private void calculateTotal() {
        totalDiscount = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double productTotal = product.getPrice() * quantity;
            double discount = calculateDiscount(product, quantity);

            totalDiscount += productTotal * discount;
            totalAmount += productTotal;
        }
    }

    public void printCheck() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm:ss");
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.print("Date;Time\n");
        System.out.print(dtf.format(LocalDateTime.now()) + "\n\n");

        System.out.print("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice();
            double discount = price * quantity * calculateDiscount(product, quantity);
            double total = price * quantity;

            System.out.print(quantity + ";" + product.getName() + ";" + df.format(price) + "$;" + df.format(discount) + "$;" + df.format(total) + "$\n");
        }

        if (discountCard != null) {
            System.out.print("\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n");
            System.out.print(discountCard.getNumber() + ";" + discountCard.getDiscountAmount() + "%" + "\n\n");
        }


        System.out.print("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
        System.out.print(df.format(totalAmount) + "$;" + df.format(totalDiscount) + "$;" + df.format(totalAmount - totalDiscount) + "$\n");
    }

    public void saveCheckToFile(String filePath) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm:ss");
        DecimalFormat df = new DecimalFormat("#0.00");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("Date;Time\n");
            writer.write(dtf.format(LocalDateTime.now()) + "\n\n");

            writer.write("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");

            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                double price = product.getPrice();
                double discount = price * quantity * calculateDiscount(product, quantity);
                double total = price * quantity;

                writer.write(quantity + ";" + product.getName() + ";" + df.format(price) + "$;" + df.format(discount) + "$;" + df.format(total) + "$\n");
            }

            if (discountCard != null) {
                writer.write("\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n");
                writer.write(discountCard.getNumber() + ";" + discountCard.getDiscountAmount() + "%" + "\n\n");
            }


            writer.write("TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
            writer.write(df.format(totalAmount) + "$;" + df.format(totalDiscount) + "$;" + df.format(totalAmount - totalDiscount) + "$\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double calculateDiscount(Product product, int quantity) {
        if (product.isWholesale() && quantity >= 5) {
            return 0.1;
        } else if (discountCard != null) {
            return (double) discountCard.getDiscountAmount() / 100;
        } else {
            return 1;
        }
    }
}
