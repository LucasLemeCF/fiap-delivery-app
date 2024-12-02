package br.com.fiap.delivery.adapters.outbound;

import br.com.fiap.delivery.core.domain.CategoryDomain;
import br.com.fiap.delivery.infra.entities.CategoryEntity;
import br.com.fiap.delivery.infra.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryRepositoryAdapterTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryRepositoryAdapter categoryRepositoryAdapter;

    @Test
    void testCreateCategorySuccess() {
        String categoryName = "Test Category";
        CategoryDomain expectedCategory = new CategoryDomain(1L, categoryName);
        CategoryEntity expectedEntity = new CategoryEntity(1L, categoryName, Collections.emptyList());

        when(categoryRepository.save(expectedEntity)).thenReturn(expectedEntity);

        CategoryDomain createdCategory = categoryRepositoryAdapter.create(expectedCategory);

        assertEquals(expectedCategory, createdCategory);
        verify(categoryRepository).save(expectedEntity);
    }

    @Test
    void testSearchCategorySuccess() {
        String categoryName = "Test Category";
        CategoryDomain expectedCategory = new CategoryDomain(1L, categoryName);
        CategoryEntity existingEntity = new CategoryEntity(1L, categoryName, Collections.emptyList());

        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(existingEntity));

        CategoryDomain foundCategory = categoryRepositoryAdapter.search(categoryName);

        assertEquals(expectedCategory, foundCategory);
    }

    @Test
    void testSearchAllCategories() {
        List<CategoryEntity> categoryEntities = Collections.singletonList(
                new CategoryEntity(1L, "Test Category", Collections.emptyList())
        );

        List<CategoryDomain> expectedCategories = new ArrayList<>();

        for (CategoryEntity entity : categoryEntities) {
            expectedCategories.add(new CategoryDomain(entity.getId(), entity.getName()));
        }

        when(categoryRepository.findAll()).thenReturn(categoryEntities);

        List<CategoryDomain> foundCategories = categoryRepositoryAdapter.searchAll();

        assertEquals(expectedCategories, foundCategories);
        verify(categoryRepository).findAll();
    }
}
