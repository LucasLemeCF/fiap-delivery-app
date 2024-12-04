package br.com.fiap.delivery.adapters.outbound;

import br.com.fiap.delivery.core.domain.CategoryDomain;
import br.com.fiap.delivery.core.domain.ProductDomain;
import br.com.fiap.delivery.core.domain.exceptions.NotFoundException;
import br.com.fiap.delivery.infra.entities.CategoryEntity;
import br.com.fiap.delivery.infra.entities.ProductEntity;
import br.com.fiap.delivery.infra.outbound.ProductRepositoryAdapter;
import br.com.fiap.delivery.infra.outbound.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryAdapterTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductRepositoryAdapter adapter;

    @Test
    public void testSearchProductSuccess() throws NotFoundException {
        Long expectedId = 1L;
        String expectedName = "Test Product";
        String expectedDescription = "This is a test product";
        BigDecimal expectedValue = BigDecimal.valueOf(10.00);
        List<ProductEntity> expectedProducts = new ArrayList<>();
        CategoryEntity expectedCategory = new CategoryEntity(1L, "Test Product", expectedProducts);
        boolean expectedIsActive = true;

        ProductEntity expectedEntity = new ProductEntity(expectedId, expectedName, expectedDescription, expectedValue, expectedCategory, expectedIsActive);

        when(productRepository.findByName(expectedName)).thenReturn(expectedEntity);

        ProductDomain actualProduct = adapter.searchProduct("Test Product");

        assertNotNull(actualProduct);
        assertEquals(expectedId, actualProduct.getId());
        assertEquals(expectedName, actualProduct.getName());

        verify(productRepository).findByName(expectedName);
    }

    @Test
    public void testCreateSuccess() {
        String expectedName = "Test Product";
        String expectedDescription = "This is a test product";
        BigDecimal expectedValue = BigDecimal.valueOf(10.00);
        List<ProductEntity> expectedProducts = new ArrayList<>();
        boolean expectedIsActive = true;

        CategoryDomain expectedCategoryDomain = new CategoryDomain(1L, "Electronics");
        CategoryEntity expectedCategoryEntity = new CategoryEntity(1L, "Doesn't Matter", expectedProducts);
        ProductEntity expectedEntity = new ProductEntity(null, expectedName, expectedDescription, expectedValue, expectedCategoryEntity, expectedIsActive);

        when(productRepository.save(argThat(entity -> entity.getCategory().getId().equals(expectedCategoryDomain.getId())))).thenReturn(expectedEntity);

        ProductRepositoryAdapter adapter = new ProductRepositoryAdapter(productRepository);
        ProductDomain newProduct = new ProductDomain(
                null,
                "Smartphone Top de Linha",
                "Smartphone com as melhores especificações do mercado.",
                BigDecimal.valueOf(5000.00),
                new CategoryDomain(1L, "Eletrônicos") ,
                true
        );

        ProductDomain createdProduct = adapter.create(newProduct);

        assertNotNull(createdProduct);
    }

    @Test
    public void testSearchAll_ReturnsEmptyList_WhenNoProductsFound() {
        ProductRepositoryAdapter adapter = new ProductRepositoryAdapter(productRepository);

        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<ProductDomain> products = adapter.searchAll();

        assertEquals(Collections.emptyList(), products);
    }

}
