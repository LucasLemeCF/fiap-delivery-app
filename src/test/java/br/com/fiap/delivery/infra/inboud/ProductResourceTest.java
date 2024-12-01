package br.com.fiap.delivery.infra.inboud;

import br.com.fiap.delivery.core.domain.CategoryDomain;
import br.com.fiap.delivery.core.domain.ProductDomain;
import br.com.fiap.delivery.core.ports.inbound.ProductPort;
import br.com.fiap.delivery.infra.inbound.ProductResource;
import br.com.fiap.delivery.infra.inbound.form.CategoryForm;
import br.com.fiap.delivery.infra.inbound.form.ProductForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductResourceTest {

    @Mock
    private ProductPort productPort;

    @InjectMocks
    private ProductResource productResource;

    @Test
    void testCreateProductSuccess() {
        ProductForm productForm = new ProductForm(
                "Product Name", "Product Description", BigDecimal.TEN, new CategoryForm("Category")
        );
        ProductDomain expectedProduct = new ProductDomain(
                null, "Product Nam", "Product Description", BigDecimal.TEN, new CategoryDomain(null, "Category"), true
        );

        when(productPort.create(expectedProduct)).thenReturn(expectedProduct);

        ResponseEntity<ProductDomain> response = productResource.createProduct(productForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProduct, response.getBody());
        verify(productPort).create(expectedProduct);
    }

    @Test
    void testSearchProductsSuccess() {
        List<ProductDomain> products = Collections.singletonList(
                new ProductDomain(1L, "Product Name", "Description", BigDecimal.ONE, new CategoryDomain(1L, "test"), true)
        );

        when(productPort.searchAll()).thenReturn(products);

        ResponseEntity<List<ProductDomain>> response = productResource.searchProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
        verify(productPort).searchAll();
    }
}