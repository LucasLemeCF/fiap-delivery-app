package br.com.fiap.delivery.core.usecases;

import br.com.fiap.delivery.core.domain.OrderDomain;
import br.com.fiap.delivery.core.domain.enums.OrderStatus;
import br.com.fiap.delivery.core.ports.inbound.OrderPort;
import br.com.fiap.delivery.core.ports.outbound.CheckoutPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CheckoutManagerTest {

    @Mock
    private CheckoutPort checkoutPort;

    @Mock
    private OrderPort orderPort;

    @InjectMocks
    private CheckoutManager checkoutManager;

    private OrderDomain mockOrder;

    @BeforeEach
    public void setUp() {
        mockOrder = new OrderDomain(1L, "Customer", LocalDateTime.now(), BigDecimal.valueOf(100), OrderStatus.IN_PREPARATION);

        when(orderPort.searchOrder(1L)).thenReturn(mockOrder);
        when(checkoutPort.checkout("Customer", BigDecimal.valueOf(100))).thenReturn(true);
    }

    @Test
    public void testCheckout() {
        checkoutManager.checkout(1L);

        verify(orderPort, times(1)).searchOrder(1L);
        verify(checkoutPort, times(1)).checkout("Customer", BigDecimal.valueOf(100));
        verify(orderPort, times(1)).updateOrder(mockOrder);

        assertEquals(OrderStatus.IN_PREPARATION, mockOrder.getStatus());
    }
}
