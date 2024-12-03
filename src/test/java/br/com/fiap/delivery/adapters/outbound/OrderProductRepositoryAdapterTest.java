package br.com.fiap.delivery.adapters.outbound;

import br.com.fiap.delivery.core.domain.OrderDomain;
import br.com.fiap.delivery.core.domain.OrderProductDomain;
import br.com.fiap.delivery.infra.entities.CategoryEntity;
import br.com.fiap.delivery.infra.entities.OrderEntity;
import br.com.fiap.delivery.core.domain.enums.OrderStatus;
import br.com.fiap.delivery.infra.entities.OrderProductEntity;
import br.com.fiap.delivery.infra.entities.ProductEntity;
import br.com.fiap.delivery.infra.outbound.OrderProductRepositoryAdapter;
import br.com.fiap.delivery.infra.outbound.repositories.OrderProductRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderProductRepositoryAdapterTest {

    @Mock
    private OrderProductRepository orderProductRepository;

    @InjectMocks
    private OrderProductRepositoryAdapter adapter;

    @Test
    public void testCreateAll_EmptyList_ReturnsEmptyList() {
        List<OrderProductDomain> orderProducts = new ArrayList<>();
        when(orderProductRepository.saveAll(any())).thenReturn(Collections.emptyList());

        List<OrderProductDomain> createdProducts = adapter.createAll(orderProducts);

        assertEquals(Collections.emptyList(), createdProducts);
    }

    @Test
    public void testFindAllBy_shouldReturnEmptyListIfNoOrdersFound() {
        LocalDateTime dateTime = LocalDateTime.now();
        OrderDomain order = new OrderDomain(1L, "John Doe", dateTime, BigDecimal.TEN, OrderStatus.RECEIVED, "valid_payment_code");
        OrderEntity orderEntity = new OrderEntity(1L, "John Doe", dateTime, BigDecimal.TEN, OrderStatus.RECEIVED, "valid_payment_code");
        List<OrderProductEntity> orderProductEntities = getOrderProductEntities(orderEntity);

        when(orderProductRepository.findAllByOrder(orderEntity)).thenReturn(orderProductEntities);

        List<OrderProductDomain> result = adapter.findAllBy(order);

        assertEquals(1, result.size());
    }

    @NotNull
    private static List<OrderProductEntity> getOrderProductEntities(OrderEntity orderEntity) {
        List<ProductEntity> products = new ArrayList<>();
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Category", products);
        ProductEntity productEntity1 = new ProductEntity(1L, "Product", "Description", BigDecimal.TEN, categoryEntity, true);
        products.add(productEntity1);
        ProductEntity productEntity2 = new ProductEntity(1L, "Product", "Description", BigDecimal.TEN, categoryEntity, true);

        List<OrderProductEntity> orderProductEntities = new ArrayList<>();
        OrderProductEntity orderProductEntity = new OrderProductEntity(1L, orderEntity, productEntity2);
        orderProductEntities.add(orderProductEntity);
        return orderProductEntities;
    }

}
