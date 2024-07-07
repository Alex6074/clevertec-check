package ru.clevertec.check;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JDBCRepository {
    private Connection connection = null;
    private PreparedStatement ps = null;

    private static final String createProductSQL = "INSERT INTO product(description, price, quantity_in_stock, wholesale_product) values (?,?,?,?);";
    private static final String updateProductSQL = "UPDATE product SET description = ?, price = ?, quantity_in_stock = ?, wholesale_product = ? WHERE id = ?;";
    private static final String deleteProductSQL = "DELETE FROM product WHERE id = ?;";
    private static final String findProductByIdSQL = "SELECT * FROM product WHERE id = ?;";

    private static final String createDiscountCardSQL = "INSERT INTO discount_card(number, amount) values (?,?);";
    private static final String updateDiscountCardSQL = "UPDATE discount_card SET number = ?, amount = ? WHERE id = ?;";
    private static final String deleteDiscountCardSQL = "DELETE FROM discount_card WHERE id = ?;";
    private static final String findDiscountCardByIdSQL = "SELECT * FROM discount_card WHERE id = ?;";
    private static final String findDiscountCardByNumberSQL = "SELECT * FROM discount_card WHERE number = ?;";

    public Long createProduct(Product product) {
        Long productId = null;
        try {
            connection = MyDataSource.getInstance().getConnection();
            if (connection != null) {
                ps = connection.prepareStatement(createProductSQL, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, product.getDescription());
                ps.setBigDecimal(2, product.getPrice());
                ps.setInt(3, product.getQuantity());
                ps.setBoolean(4, product.isWholesale());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    productId = rs.getLong(1);
                }
                ps.close();
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productId;
    }

    public Product updateProduct(Product product) {
        try {
            connection = MyDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(updateProductSQL);
            ps.setString(1, product.getDescription());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setBoolean(4, product.isWholesale());
            ps.setLong(5, product.getId());
            if(ps.executeUpdate() > 0) {
                ps.close();
                connection.close();
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Product findProductById(Long productId) {
        Product product = new Product();
        try {
            connection = MyDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(findProductByIdSQL);
            ps.setLong(1, productId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                product.setId(resultSet.getLong("id"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setQuantity(resultSet.getInt("quantity_in_stock"));
                product.setWholesale(resultSet.getBoolean("wholesale_product"));
            }

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }


    public void deleteProduct(Long productId) {
        try{
            connection = MyDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(deleteProductSQL);
            ps.setLong(1, productId);
            ps.executeUpdate();

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long createDiscountCard(DiscountCard discountCard) {
        Long cardId = null;
        try {
            connection = MyDataSource.getInstance().getConnection();
            if (connection != null) {
                ps = connection.prepareStatement(createDiscountCardSQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, discountCard.getNumber());
                ps.setInt(2, discountCard.getDiscountAmount());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    cardId = rs.getLong(1);
                }
                ps.close();
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cardId;
    }

    public DiscountCard updateDiscountCard(DiscountCard discountCard) {
        try {
            connection = MyDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(updateDiscountCardSQL);
            ps.setInt(1, discountCard.getNumber());
            ps.setInt(2, discountCard.getDiscountAmount());
            ps.setLong(3, discountCard.getId());

            if(ps.executeUpdate() > 0) {
                ps.close();
                connection.close();
                return discountCard;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public DiscountCard findDiscountCardById(Long discountCardId) {
        DiscountCard discountCard = new DiscountCard();
        try {
            connection = MyDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(findDiscountCardByIdSQL);
            ps.setLong(1, discountCardId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                discountCard.setId(resultSet.getLong("id"));
                discountCard.setNumber(resultSet.getInt("number"));
                discountCard.setDiscountAmount(resultSet.getShort("amount"));
            }

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return discountCard;
    }

    public DiscountCard findDiscountCardByNumber(int discountCardNumber) {
        DiscountCard discountCard = new DiscountCard();
        try {
            connection = MyDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(findDiscountCardByNumberSQL);
            ps.setInt(1, discountCardNumber);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                discountCard.setId(resultSet.getLong("id"));
                discountCard.setNumber(resultSet.getInt("number"));
                discountCard.setDiscountAmount(resultSet.getShort("amount"));
            }

            ps.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return discountCard;
    }


    public void deleteDiscountCard(Long cardId) {
        try{
            connection = MyDataSource.getInstance().getConnection();
            ps = connection.prepareStatement(deleteDiscountCardSQL);
            ps.setLong(1, cardId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
