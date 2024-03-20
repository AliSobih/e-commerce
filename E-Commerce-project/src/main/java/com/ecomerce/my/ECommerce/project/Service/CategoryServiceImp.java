package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.entity.Category;
import com.ecomerce.my.ECommerce.project.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService{
    private final CategoryRepo categoryRepo;
    @Override
    public Category createCategory(String name) {
        Category category = categoryRepo.findCategoryByName(name).orElse(null);
        if (category != null) {
            return null;
        }
        category = new Category(name);
        return categoryRepo.save(category);
    }

    @Override
    public boolean deleteCategory(String name) {
        Category category = categoryRepo.findCategoryByName(name).orElse(null);
        if (category == null) {
            return false;
        }
        categoryRepo.delete(category);
        return true;
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findCategoryByName(name).orElse(null);
    }
}
