package br.com.fiap.delivery.core.usecases;

import br.com.fiap.delivery.core.domain.CategoryDomain;
import br.com.fiap.delivery.core.ports.outbound.CategoryRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryManagerTest {

    @Mock
    private CategoryRepositoryPort categoryRepositoryPort;

    @InjectMocks
    private CategoryManager categoryManager;

    @Test
    void testCreateCategorySuccess() {
        CategoryDomain categoryDomain = new CategoryDomain(null, "Electronics");
        CategoryDomain expectedCategory = new CategoryDomain(1L, "Electronics");
        when(categoryRepositoryPort.create(categoryDomain)).thenReturn(expectedCategory);

        CategoryDomain createdCategory = categoryManager.create(categoryDomain);

        assertEquals(expectedCategory, createdCategory);
        verify(categoryRepositoryPort).create(categoryDomain);
    }

    @Test
    void testSearchCategorySuccess() {
        String categoryName = "Books";
        CategoryDomain expectedCategory = new CategoryDomain(2L, "Books");
        when(categoryRepositoryPort.search(categoryName)).thenReturn(expectedCategory);

        CategoryDomain foundCategory = categoryManager.search(categoryName);

        assertEquals(expectedCategory, foundCategory);
        verify(categoryRepositoryPort).search(categoryName);
    }

    @Test
    void testSearchAllCategoriesSuccess() {
        List<CategoryDomain> categories = Collections.singletonList(new CategoryDomain(3L, "Clothing"));
        when(categoryRepositoryPort.searchAll()).thenReturn(categories);

        List<CategoryDomain> allCategories = categoryManager.searchAll();

        assertEquals(categories, allCategories);
        verify(categoryRepositoryPort).searchAll();
    }
}