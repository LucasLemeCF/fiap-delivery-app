package br.com.fiap.delivery.adapters.outbound;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CheckoutAdapterTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private CheckoutAdapter checkoutAdapter;

    @Test
    void testCheckoutSuccess() {
        String customer = "John Doe";
        BigDecimal value = BigDecimal.TEN;

        boolean checkoutResult = checkoutAdapter.checkout(customer, value);

        assertTrue(checkoutResult);
    }
}