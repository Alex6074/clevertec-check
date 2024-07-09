package ru.clevertec.check.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.clevertec.check.core.DiscountCard;
import ru.clevertec.check.core.Product;

import java.math.BigDecimal;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TODO Use H2 database
 */
class JDBCRepositoryTest {
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet resultSet;

    private JDBCRepository jdbcRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MyDataSource.initialize("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/clevertec_check", "for_java_connection", "password");
        jdbcRepository = new JDBCRepository();
    }

    @Test
    void testFindProductById() throws SQLException {
        Product expectedProduct = new Product(1L, "Milk", BigDecimal.valueOf(1.07), 10, true);
        when(ps.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expectedProduct.getId());
        when(resultSet.getString("description")).thenReturn(expectedProduct.getDescription());
        when(resultSet.getBigDecimal("price")).thenReturn(expectedProduct.getPrice());
        when(resultSet.getInt("quantity_in_stock")).thenReturn(expectedProduct.getQuantity());
        when(resultSet.getBoolean("wholesale_product")).thenReturn(expectedProduct.isWholesale());

        Product product = jdbcRepository.findProductById(1L);

        assertNotNull(product);
        assertEquals(expectedProduct, product);
    }

    @Test
    void testFindDiscountCardById() throws SQLException {
        DiscountCard expectedCard = new DiscountCard(1, 1111, (short) 3);
        when(ps.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expectedCard.getId());
        when(resultSet.getInt("number")).thenReturn(expectedCard.getNumber());
        when(resultSet.getShort("amount")).thenReturn(expectedCard.getDiscountAmount());

        DiscountCard card = jdbcRepository.findDiscountCardById(1L);

        assertNotNull(card);
        assertEquals(expectedCard, card);
    }

    @Test
    void testFindDiscountCardByNumber() throws SQLException {
        DiscountCard expectedCard = new DiscountCard(1, 1111, (short) 3);
        when(ps.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(expectedCard.getId());
        when(resultSet.getInt("number")).thenReturn(expectedCard.getNumber());
        when(resultSet.getShort("amount")).thenReturn(expectedCard.getDiscountAmount());

        DiscountCard card = jdbcRepository.findDiscountCardByNumber(1111);

        assertNotNull(card);
        assertEquals(expectedCard, card);
    }


}