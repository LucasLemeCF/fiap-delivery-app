package br.com.fiap.delivery.core.usecases;

import br.com.fiap.delivery.core.domain.CategoryDomain;
import br.com.fiap.delivery.core.domain.ProductDomain;
import br.com.fiap.delivery.core.ports.inbound.CategoryPort;
import br.com.fiap.delivery.core.ports.outbound.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductManagerTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @Mock
    private CategoryPort categoryPort;

    @InjectMocks
    private ProductManager productManager;

    @Test
    void testSearchProductSuccess() {
        String productName = "Test Product";
        ProductDomain expectedProduct = new ProductDomain(1L, productName, "...", BigDecimal.ZERO, new CategoryDomain(1L, "Category"), true);

        when(productRepositoryPort.searchProduct(productName)).thenReturn(expectedProduct);

        ProductDomain foundProduct = productManager.searchProduct(productName);

        assertEquals(expectedProduct, foundProduct);
    }

    @Test
    void testCreateProductSuccess() {
        String productName = "Test Product";
        String categoryName = "Category Name";
        ProductDomain productDomain = new ProductDomain(null, productName, "...", BigDecimal.ZERO, new CategoryDomain(null, categoryName), true);
        CategoryDomain expectedCategory = new CategoryDomain(1L, categoryName);
        ProductDomain expectedSavedProduct = new ProductDomain(1L, productName, "...", BigDecimal.ZERO, expectedCategory, true);

        when(categoryPort.search(categoryName)).thenReturn(expectedCategory);
        when(productRepositoryPort.create(productDomain)).thenReturn(expectedSavedProduct);

        ProductDomain createdProduct = productManager.create(productDomain);

        verify(categoryPort).search(categoryName);
        assertEquals(expectedCategory, productDomain.getCategory());
        verify(productRepositoryPort).create(productDomain);
        assertEquals(expectedSavedProduct, createdProduct);
    }

    @Test
    void testSearchAllProducts() {
        List<ProductDomain> expectedProducts = Collections.singletonList(
                new ProductDomain(1L, "Product 1", "...", BigDecimal.ZERO, new CategoryDomain(1L, "Category"), true)
        );

        when(productRepositoryPort.searchAll()).thenReturn(expectedProducts);

        List<ProductDomain> foundProducts = productManager.searchAll();

        assertEquals(expectedProducts, foundProducts);
    }
}
