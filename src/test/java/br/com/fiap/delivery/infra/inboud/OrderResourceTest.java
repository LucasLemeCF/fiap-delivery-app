package br.com.fiap.delivery.infra.inboud;

import br.com.fiap.delivery.core.domain.*;
import br.com.fiap.delivery.core.domain.enums.OrderStatus;
import br.com.fiap.delivery.core.ports.inbound.OrderPort;
import br.com.fiap.delivery.infra.inbound.OrderResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderResourceTest {

    @Mock
    private OrderPort orderPort;

    @InjectMocks
    private OrderResource orderResource;

    @Test
    void testCreateOrder_Success() {
        CustomerDomain customerDomain = new CustomerDomain("Customer Name", "12345678901", "teste@email.com");
        OrderFlatDomain orderFlatDomain = new OrderFlatDomain(
                customerDomain,
                List.of(new ProductFlatDomain("Product 1", 1L))
        );

        OrderDomain expectedOrder = new OrderDomain(
                null, "Customer Name", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.RECEIVED, "12345"
        );

        when(orderPort.createOrder(orderFlatDomain)).thenReturn(expectedOrder);

        ResponseEntity<OrderDomain> response = orderResource.createOrder(orderFlatDomain);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedOrder, response.getBody());
        verify(orderPort).createOrder(orderFlatDomain);
    }

    @Test
    void testGetOrderByIdSuccess() {
        Long orderId = 1L;
        OrderDomain order = new OrderDomain(orderId, "John Doe", LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.RECEIVED, "12345");
        List<ProductDomain> products = Collections.emptyList();
        CompleteOrderDomain expectedResponse = new CompleteOrderDomain(order, products);
        when(orderPort.getOrderBy(orderId)).thenReturn(expectedResponse);

        ResponseEntity<CompleteOrderDomain> response = orderResource.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(orderPort).getOrderBy(orderId);
    }
}