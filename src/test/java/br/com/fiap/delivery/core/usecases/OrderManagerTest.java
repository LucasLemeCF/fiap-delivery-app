package br.com.fiap.delivery.core.usecases;
import br.com.fiap.delivery.core.domain.*;
import br.com.fiap.delivery.core.domain.enums.OrderStatus;
import br.com.fiap.delivery.core.ports.inbound.ProductPort;
import br.com.fiap.delivery.core.ports.outbound.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private OrderManager orderManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        orderManager = new OrderManager(customerPort, productPort, orderRepositoryPort, orderProductRepositoryPort, checkoutPort);
    }

    @Test
    public void testCreateOrder_ValidCustomerAndProducts_ReturnsCreatedOrder() {
        String customerCpf = "12345678900";
        CustomerDomain customer = new CustomerDomain("John Doe", customerCpf, "teste@email.com");
        when(customerPort.check(customer)).thenReturn(customer);
        when(orderRepositoryPort.createOrder(any(OrderDomain.class))).thenReturn(new OrderDomain(1L, customerCpf, LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.WAITING_PAYMENT, null));

        ProductDomain product1 = new ProductDomain(1L, "Product 1", "description", BigDecimal.TEN, new CategoryDomain(1L, "Category"), true);
        ProductDomain product2 = new ProductDomain(2L, "Product 2", "description", BigDecimal.valueOf(5), new CategoryDomain(1L, "Category"), true);
        List<ProductFlatDomain> products = new ArrayList<>();
        products.add(new ProductFlatDomain("Product 1", 2));
        products.add(new ProductFlatDomain("Product 2", 1));
        when(productPort.searchProduct("Product 1")).thenReturn(product1);
        when(productPort.searchProduct("Product 2")).thenReturn(product2);

        String paymentCode = "valid_payment_code";
        when(checkoutPort.checkout(any(BigDecimal.class))).thenReturn(paymentCode);
        when(orderRepositoryPort.update(any(OrderDomain.class))).thenReturn(new OrderDomain(1L, customerCpf, LocalDateTime.now(), BigDecimal.ZERO, OrderStatus.WAITING_PAYMENT, paymentCode));

        OrderDomain expectedOrder = new OrderDomain(
                null, customerCpf, LocalDateTime.now(), BigDecimal.valueOf(25), OrderStatus.WAITING_PAYMENT, paymentCode);

        OrderDomain actualOrder = orderManager.createOrder(new OrderFlatDomain(customer, products));

        assertNotNull(actualOrder.getId());
        assertEquals(expectedOrder.getCustomer(), actualOrder.getCustomer());
        assertEquals(expectedOrder.getStatus(), actualOrder.getStatus());
        assertEquals(expectedOrder.getPaymentCode(), actualOrder.getPaymentCode());

        verify(productPort).searchProduct("Product 1");
        verify(productPort).searchProduct("Product 2");
    }

    @Test
    public void testGetOrderBy_ExistingOrder_ReturnsCompleteOrder() {
        Long orderId = 1L;
        OrderDomain order = new OrderDomain(orderId, "John Doe", LocalDateTime.now(), BigDecimal.TEN, OrderStatus.RECEIVED, "payment_code");
        ProductDomain product = new ProductDomain(1L, "Product", "description", BigDecimal.TEN, new CategoryDomain(1L, "Category"), true);
        List<OrderProductDomain> products = List.of(new OrderProductDomain(1L, order, product));
        when(orderRepositoryPort.searchBy(orderId)).thenReturn(order);
        when(orderProductRepositoryPort.findAllBy(order)).thenReturn(products);

        CompleteOrderDomain result = orderManager.getOrderBy(orderId);

        assertNotNull(result);
    }

    @Test
    public void testSearchOrder_ExistingOrder_ReturnsOrder() {
        Long orderId = 1L;
        OrderDomain expectedOrder = new OrderDomain(orderId, "John Doe", LocalDateTime.now(), BigDecimal.TEN, OrderStatus.RECEIVED, "payment_code");
        when(orderRepositoryPort.searchBy(orderId)).thenReturn(expectedOrder);

        OrderDomain actualOrder = orderManager.searchOrder(orderId);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testSearchOrderBy_ExistingPaymentCode_ReturnsOrder() {
        String paymentCode = "payment123";
        OrderDomain expectedOrder = new OrderDomain(1L, "John Doe", LocalDateTime.now(), BigDecimal.TEN, OrderStatus.RECEIVED, paymentCode);
        when(orderRepositoryPort.searchBy(paymentCode)).thenReturn(expectedOrder);

        OrderDomain actualOrder = orderManager.searchOrderBy(paymentCode);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testUpdateOrder_UpdatesOrderSuccessfully() {
        OrderDomain orderToUpdate = new OrderDomain(1L, "John Doe", LocalDateTime.now(), BigDecimal.TEN, OrderStatus.RECEIVED, "payment_code");
        OrderDomain updatedOrder = new OrderDomain(1L, "Jane Doe", LocalDateTime.now(), BigDecimal.TWO, OrderStatus.IN_PREPARATION, "payment_code");
        when(orderRepositoryPort.update(orderToUpdate)).thenReturn(updatedOrder);

        OrderDomain result = orderManager.updateOrder(orderToUpdate);

        assertEquals(updatedOrder, result);
    }

    @Test
    public void testFinalizeOrder_ValidStatus_UpdatesStatusToFinished() {
        Long orderId = 1L;
        OrderDomain order = new OrderDomain(orderId, "John Doe", LocalDateTime.now(), BigDecimal.TEN, OrderStatus.IN_PREPARATION, "payment_code");
        when(orderRepositoryPort.searchBy(orderId)).thenReturn(order);
        when(orderRepositoryPort.update(order)).thenReturn(order);

        OrderDomain finalizedOrder = orderManager.finalizeOrder(orderId);

        assertEquals(OrderStatus.FINISHED, finalizedOrder.getStatus());
        verify(orderRepositoryPort).update(finalizedOrder);
    }

}
