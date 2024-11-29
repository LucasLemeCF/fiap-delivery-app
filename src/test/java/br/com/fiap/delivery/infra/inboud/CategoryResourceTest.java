package br.com.fiap.delivery.infra.inboud;

import br.com.fiap.delivery.core.domain.CategoryDomain;
import br.com.fiap.delivery.core.ports.inbound.CategoryPort;
import br.com.fiap.delivery.infra.inbound.CategoryResource;
import br.com.fiap.delivery.infra.inbound.form.CategoryForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryResourceTest {

    @Mock
    private CategoryPort categoryPort;

    @InjectMocks
    private CategoryResource categoryResource;

    @Test
    void testeShouldCreateCategory() {
        CategoryForm categoryForm = new CategoryForm("Categoria Teste");
        CategoryDomain categoryDomain = new CategoryDomain(1L, "Categoria Teste");

        when(categoryPort.create(any(CategoryDomain.class))).thenReturn(categoryDomain);

        ResponseEntity<CategoryDomain> response = categoryResource.create(categoryForm);

        assertEquals(ResponseEntity.ok(categoryDomain), response);
        verify(categoryPort, times(1)).create(any(CategoryDomain.class));
    }

    @Test
    void testSearchCategories() {
        List<CategoryDomain> mockCategories = List.of(
                new CategoryDomain(1L, "Category 1"),
                new CategoryDomain(2L, "Category 2")
        );

        when(categoryPort.searchAll()).thenReturn(mockCategories);

        ResponseEntity<List<CategoryDomain>> response = categoryResource.searchCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCategories, response.getBody());
    }

}
