package br.com.fiap.delivery.adapters.outbound;

import br.com.fiap.delivery.core.domain.OrderDomain;
import br.com.fiap.delivery.core.domain.enums.OrderStatus;
import br.com.fiap.delivery.infra.entities.OrderEntity;
import br.com.fiap.delivery.infra.outbound.OrderRepositoryAdapter;
import br.com.fiap.delivery.infra.outbound.repositories.OrderRepository;
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

        OrderDomain order = new OrderDomain(orderId, customerName, creationAt, price, status, "12345");
        OrderEntity expectedEntity = new OrderEntity(orderId, customerName, creationAt, price, status, "12345");

        when(orderRepository.save(expectedEntity)).thenReturn(expectedEntity);

        OrderDomain createdOrder = orderRepositoryAdapter.createOrder(order);

        assertEquals(order, createdOrder);
    }

    @Test
    void testSearchBySuccess() {
        Long orderId = 1L;
        OrderEntity expectedEntity = new OrderEntity(orderId, "Test Customer", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.RECEIVED, "12345");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedEntity));

        orderRepositoryAdapter.searchBy(orderId);

        verify(orderRepository).findById(orderId);
    }

    @Test
    void testSearchBySuccessString() {
        Long orderId = 1L;
        String paymentCode = "valid_payment_code";
        OrderEntity expectedEntity = new OrderEntity(orderId, "Test Customer", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.RECEIVED, paymentCode);

        when(orderRepository.findByPaymentCode(paymentCode)).thenReturn(Optional.of(expectedEntity));

        orderRepositoryAdapter.searchBy(paymentCode);

        verify(orderRepository).findByPaymentCode(paymentCode);
    }

    @Test
    void testUpdateSuccess() {
        Long orderId = 1L;
        String initialCustomerName = "Old Customer";
        String updatedCustomerName = "Updated Customer";
        BigDecimal initialPrice = BigDecimal.ZERO;
        BigDecimal updatedPrice = BigDecimal.valueOf(100.00);
        OrderStatus initialStatus = OrderStatus.RECEIVED;
        OrderStatus updatedStatus = OrderStatus.WAITING_PAYMENT;

        LocalDateTime creationAt = LocalDateTime.now();

        OrderDomain initialOrder = new OrderDomain(orderId, initialCustomerName, creationAt, initialPrice, initialStatus, "12345");
        OrderEntity initialEntity = new OrderEntity(orderId, initialCustomerName, creationAt, initialPrice, initialStatus, "12345");
        OrderEntity updatedEntity = new OrderEntity(orderId, updatedCustomerName, creationAt, updatedPrice, updatedStatus, "12345");

        when(orderRepository.save(initialEntity)).thenReturn(updatedEntity);

        orderRepositoryAdapter.update(initialOrder);

        verify(orderRepository).save(initialEntity);
    }
}