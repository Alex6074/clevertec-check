package ru.clevertec.check;

import lombok.Getter;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Check {
    private Map<Product, Integer> products = new HashMap<>();
    private DiscountCard discountCard;
    private double totalAmount;
    private double totalDiscount;
    private double balanceDebitCard;
    private JDBCRepository repository;

    public Check(String[] args, String url, String username, String password) {
        balanceDebitCard = Double.MIN_VALUE;
        MyDataSource.initialize("org.postgresql.Driver", url, username, password);
        repository = new JDBCRepository();

        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                int cardNumber = Integer.parseInt(arg.split("=")[1]);
                discountCard = repository.findDiscountCardByNumber(cardNumber);
                discountCard = discountCard.getId() == 0
                        ? new DiscountCard(5L, cardNumber, (short) 2)
                        : discountCard;
            } else if (arg.startsWith("balanceDebitCard=")) {
                balanceDebitCard = Double.parseDouble(arg.split("=")[1]);
            } else if (arg.startsWith("saveToFile=")) {
                continue;
            } else if (arg.startsWith("datasource.url=")) {
                continue;
            } else if (arg.startsWith("datasource.username=")) {
                continue;
            } else if (arg.startsWith("datasource.password=")) {
                continue;
            } else {
                String[] parts = arg.split("-");
                long productId = Long.parseLong(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                Product product = repository.findProductById(productId);
                products.put(product, products.getOrDefault(product, 0) + quantity);
            }
        }

        if (!products.containsKey(null))
            calculateTotal();
    }

    private void calculateTotal() {
        totalDiscount = 0;
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double productTotal = product.getPrice().doubleValue() * quantity;
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
            double price = product.getPrice().doubleValue();
            double discount = price * quantity * calculateDiscount(product, quantity);
            double total = price * quantity;

            System.out.print(quantity + ";" + product.getDescription() + ";" + df.format(price) + "$;" + df.format(discount) + "$;" + df.format(total) + "$\n");
        }

        if (discountCard != null) {
            System.out.print("\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n");
            System.out.print(discountCard.getNumber() + ";" + discountCard.getDiscountAmount() + "%" + "\n");
        }


        System.out.print("\nTOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
        System.out.print(df.format(totalAmount) + "$;"
                + df.format(totalDiscount) + "$;"
                + df.format(totalAmount - totalDiscount) + "$\n");

        System.out.print("\nBALANCE ON THE CARD\n");
        System.out.print(df.format(balanceDebitCard - totalAmount + totalDiscount) + "$");
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
                double price = product.getPrice().doubleValue();
                double discount = price * quantity * calculateDiscount(product, quantity);
                double total = price * quantity;

                writer.write(quantity + ";" + product.getDescription() + ";" + df.format(price) + "$;" + df.format(discount) + "$;" + df.format(total) + "$\n");
            }

            if (discountCard != null) {
                writer.write("\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n");
                writer.write(discountCard.getNumber() + ";" + discountCard.getDiscountAmount() + "%" + "\n");
            }


            writer.write("\nTOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
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
            return 0;
        }
    }
}
