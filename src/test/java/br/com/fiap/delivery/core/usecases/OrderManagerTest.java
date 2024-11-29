package br.com.fiap.delivery.core.usecases;
import br.com.fiap.delivery.core.domain.*;
import br.com.fiap.delivery.core.domain.enums.OrderStatus;
import br.com.fiap.delivery.core.ports.inbound.ProductPort;
import br.com.fiap.delivery.core.ports.outbound.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderManagerTest {

    @Mock
    private CustomerPort customerPort;

    @Mock
    private ProductPort productPort;

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Mock
    private OrderProductRepositoryPort orderProductRepositoryPort;

    @Mock
    private CheckoutPort checkoutPort; // Adicionado mock para CheckoutPort

    @InjectMocks
    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        OrderDomain mockOrder = new OrderDomain(1L, "John Doe", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.WAITING_PAYMENT);
        ProductDomain mockProduct = new ProductDomain(1L, "Product Name", "...", BigDecimal.ZERO, new CategoryDomain(1L, "Category"), true);

        when(productPort.searchProduct("Product Name")).thenReturn(mockProduct);
        when(orderRepositoryPort.createOrder(any(OrderDomain.class))).thenReturn(mockOrder);
    }

    @Test
    void testCreateOrderSuccess() {
        String customer = "John Doe";
        List<ProductFlatDomain> products = Collections.singletonList(new ProductFlatDomain("Product Name", 1L));
        OrderDomain expectedOrder = new OrderDomain(null, customer, LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.WAITING_PAYMENT);

        OrderDomain createdOrder = orderManager.createOrder(new OrderFlatDomain(customer, products));

        verify(customerPort).check(customer);
        verify(productPort).searchProduct("Product Name");
        verify(orderProductRepositoryPort).createAll(anyList());

        assertEquals(expectedOrder.getCustomer(), createdOrder.getCustomer());
        assertEquals(expectedOrder.getStatus(), createdOrder.getStatus());
    }
}
