package ru.clevertec.check.validation;

import org.junit.jupiter.api.Test;
import ru.clevertec.check.core.Check;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotEnoughMoneyValidationTest {
    @Test
    void testValidateEnoughMoney() {
        Check check = mock(Check.class);
        when(check.getBalanceDebitCard()).thenReturn(100.0);
        when(check.getTotalAmount()).thenReturn(50.0);
        when(check.getTotalDiscount()).thenReturn(10.0);

        NotEnoughMoneyValidation validation = new NotEnoughMoneyValidation();
        Optional<ValidationError> result = validation.validate(check);

        assertFalse(result.isPresent());

        verify(check).getBalanceDebitCard();
        verify(check).getTotalAmount();
        verify(check).getTotalDiscount();
        verifyNoMoreInteractions(check);
    }

    @Test
    void testValidateNotEnoughMoney() {
        Check check = mock(Check.class);
        when(check.getBalanceDebitCard()).thenReturn(50.0);
        when(check.getTotalAmount()).thenReturn(100.0);
        when(check.getTotalDiscount()).thenReturn(10.0);

        NotEnoughMoneyValidation validation = new NotEnoughMoneyValidation();
        Optional<ValidationError> result = validation.validate(check);

        assertTrue(result.isPresent());
        assertEquals("NOT ENOUGH MONEY", result.get().getErrorCode());
        assertEquals("Lack of funds (balance less than the amount on the check)", result.get().getDescription());

        verify(check).getBalanceDebitCard();
        verify(check).getTotalAmount();
        verify(check).getTotalDiscount();
        verifyNoMoreInteractions(check);
    }
}