package com.ecomerce.my.ECommerce.project.Service;
import com.ecomerce.my.ECommerce.project.entity.Category;
import com.ecomerce.my.ECommerce.project.repository.CategoryRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImpTest {

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryServiceImp categoryService;

    public CategoryServiceImpTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory_Success() {
        String categoryName = "Electronics";
        when(categoryRepo.findCategoryByName(categoryName)).thenReturn(Optional.empty());
        Category category = new Category();
        category.setName(categoryName);
        when(categoryRepo.save(any())).thenReturn(category);

        Category createdCategory = categoryService.createCategory(categoryName);

        assertNotNull(createdCategory); // This assertion failed
        assertEquals(categoryName, createdCategory.getName());
        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    void testCreateCategory_CategoryAlreadyExists() {
        String categoryName = "Electronics";
        Category existingCategory = new Category(categoryName);
        when(categoryRepo.findCategoryByName(categoryName)).thenReturn(Optional.of(existingCategory));

        Category createdCategory = categoryService.createCategory(categoryName);

        assertNull(createdCategory);
        verify(categoryRepo, never()).save(any(Category.class));
    }

    @Test
    void testDeleteCategory_Success() {
        String categoryName = "Electronics";
        Category existingCategory = new Category(categoryName);
        when(categoryRepo.findCategoryByName(categoryName)).thenReturn(Optional.of(existingCategory));

        boolean result = categoryService.deleteCategory(categoryName);

        assertTrue(result);
        verify(categoryRepo, times(1)).delete(existingCategory);
    }

    @Test
    void testDeleteCategory_CategoryNotFound() {
        String categoryName = "Electronics";
        when(categoryRepo.findCategoryByName(categoryName)).thenReturn(Optional.empty());

        boolean result = categoryService.deleteCategory(categoryName);

        assertFalse(result);
        verify(categoryRepo, never()).delete(any(Category.class));
    }

    @Test
    void testGetCategoryByName_Success() {
        String categoryName = "Electronics";
        Category existingCategory = new Category(categoryName);
        when(categoryRepo.findCategoryByName(categoryName)).thenReturn(Optional.of(existingCategory));

        Category retrievedCategory = categoryService.getCategoryByName(categoryName);

        assertNotNull(retrievedCategory);
        assertEquals(categoryName, retrievedCategory.getName());
    }

    @Test
    void testGetCategoryByName_CategoryNotFound() {
        String categoryName = "Electronics";
        when(categoryRepo.findCategoryByName(categoryName)).thenReturn(Optional.empty());

        Category retrievedCategory = categoryService.getCategoryByName(categoryName);

        assertNull(retrievedCategory);
    }
}
