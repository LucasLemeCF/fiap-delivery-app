package br.com.fiap.delivery.adapters.outbound;

import br.com.fiap.delivery.core.domain.OrderDomain;
import br.com.fiap.delivery.core.domain.enums.OrderStatus;
import br.com.fiap.delivery.infra.entities.OrderEntity;
import br.com.fiap.delivery.infra.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryAdapterTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderRepositoryAdapter orderRepositoryAdapter;

    @Test
    void testCreateOrderSuccess() {
        Long orderId = 1L;
        String customerName = "Test Customer";
        BigDecimal price = BigDecimal.valueOf(100.00);
        OrderStatus status = OrderStatus.RECEIVED;

        LocalDateTime creationAt = LocalDateTime.now(); // Capture the actual time

        OrderDomain order = new OrderDomain(orderId, customerName, creationAt, price, status);
        OrderEntity expectedEntity = new OrderEntity(orderId, customerName, creationAt, price, status);

        when(orderRepository.save(expectedEntity)).thenReturn(expectedEntity);

        OrderDomain createdOrder = orderRepositoryAdapter.createOrder(order);

        assertEquals(order, createdOrder);
    }

    @Test
    void testSearchBySuccess() {
        Long orderId = 1L;
        OrderEntity expectedEntity = new OrderEntity(orderId, "Test Customer", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.RECEIVED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedEntity));

        orderRepositoryAdapter.searchBy(orderId);

        verify(orderRepository).findById(orderId);
    }
}