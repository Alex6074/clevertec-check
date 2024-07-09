package ru.clevertec.check;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BadRequestValidationTest {
    @Test
    void testValidateEmptyProducts() {
        Check check = mock(Check.class);
        when(check.getProducts()).thenReturn(new HashMap<>());

        BadRequestValidation validation = new BadRequestValidation();
        Optional<ValidationError> result = validation.validate(check);

        assertTrue(result.isPresent());
        assertEquals("BAD REQUEST", result.get().getErrorCode());
        assertEquals("Arguments filled in incorrectly (missing products)", result.get().getDescription());

        verify(check).getProducts();
        verifyNoMoreInteractions(check);
    }

    @Test
    void testValidateNullProductKey() {
        Check check = mock(Check.class);
        Map<Product, Integer> products = new HashMap<>();
        products.put(null, 5);
        when(check.getProducts()).thenReturn(products);

        BadRequestValidation validation = new BadRequestValidation();
        Optional<ValidationError> result = validation.validate(check);

        assertTrue(result.isPresent());
        assertEquals("BAD REQUEST", result.get().getErrorCode());
        assertEquals("Arguments filled in incorrectly (missing products)", result.get().getDescription());

        verify(check).getProducts();
    }

    @Test
    void testValidateQuantityErrors() {
        Check check = mock(Check.class);
        Map<Product, Integer> products = new HashMap<>();
        products.put(new Product(), 10);
        when(check.getProducts()).thenReturn(products);

        JDBCRepository repository = mock(JDBCRepository.class);
        when(check.getRepository()).thenReturn(repository);
        when(repository.findProductById(anyLong())).thenReturn(new Product());

        BadRequestValidation validation = new BadRequestValidation();
        Optional<ValidationError> result = validation.validate(check);

        assertTrue(result.isPresent());
        assertEquals("BAD REQUEST", result.get().getErrorCode());
        assertEquals("Arguments filled in incorrectly (quantity errors)", result.get().getDescription());
    }
}