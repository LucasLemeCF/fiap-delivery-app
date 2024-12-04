package br.com.fiap.delivery.core.usecases;

import br.com.fiap.delivery.core.domain.OrderDomain;
import br.com.fiap.delivery.core.domain.enums.OrderStatus;
import br.com.fiap.delivery.core.ports.inbound.OrderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CheckoutManagerTest {

    @Mock
    private OrderPort orderPort;

    private CheckoutManager checkoutManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        checkoutManager = new CheckoutManager(orderPort);
    }

    @Test
    public void testConfirmCheckout_ValidPaymentCode_UpdatesOrderStatusAndReturnsOrder() {
        String paymentCode = "valid_code";
        OrderDomain expectedOrder = new OrderDomain(1L, "John Doe", LocalDateTime.now(), BigDecimal.TEN, OrderStatus.RECEIVED, paymentCode);
        when(orderPort.searchOrderBy(paymentCode)).thenReturn(expectedOrder);
        when(orderPort.updateOrder(expectedOrder)).thenReturn(expectedOrder);

        OrderDomain actualOrder = checkoutManager.confirmCheckout(paymentCode);

        assertEquals(OrderStatus.IN_PREPARATION, actualOrder.getStatus());
    }

}
