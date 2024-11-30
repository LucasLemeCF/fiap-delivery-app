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
    private CheckoutPort checkoutPort;

    @InjectMocks
    private OrderManager orderManager;

    @Test
    void testCreateOrderSuccess() {
        OrderDomain mockOrder = new OrderDomain(1L, "John Doe", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.WAITING_PAYMENT);
        ProductDomain mockProduct = new ProductDomain(1L, "Product Name", "...", BigDecimal.ZERO, new CategoryDomain(1L, "Category"), true);

        when(productPort.searchProduct("Product Name")).thenReturn(mockProduct);
        when(orderRepositoryPort.createOrder(any(OrderDomain.class))).thenReturn(mockOrder);

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

    @Test
    void testGetOrderBySuccess() {
        OrderDomain order = new OrderDomain(1L, "John Doe", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.RECEIVED);
        List<OrderProductDomain> orderProducts = Collections.singletonList(
                new OrderProductDomain(1L, order, new ProductDomain(1L, "Product 1", "Description", BigDecimal.TEN, new CategoryDomain(1L, "Category"), true))
        );
        when(orderRepositoryPort.searchBy(1L)).thenReturn(order);
        when(orderProductRepositoryPort.findAllBy(order)).thenReturn(orderProducts);

        CompleteOrderDomain completeOrder = orderManager.getOrderBy(1L);

        assertEquals(order, completeOrder.getOrder());
        assertEquals(orderProducts.getFirst().getProduct(), completeOrder.getProducts().getFirst());
    }

    @Test
    void testSearchOrderSuccess() {
        Long orderId = 1L;
        OrderDomain order = new OrderDomain(orderId, "John Doe", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.RECEIVED);
        when(orderRepositoryPort.searchBy(orderId)).thenReturn(order);

        OrderDomain foundOrder = orderManager.searchOrder(orderId);

        assertEquals(order, foundOrder);
    }

    @Test
    void testUpdateOrderSuccess() {
        OrderDomain order = new OrderDomain(1L, "John Doe", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.RECEIVED);
        when(orderRepositoryPort.update(order)).thenReturn(order);

        OrderDomain updatedOrder = orderManager.updateOrder(order);

        assertEquals(order, updatedOrder);
        verify(orderRepositoryPort).update(order);
    }
}
