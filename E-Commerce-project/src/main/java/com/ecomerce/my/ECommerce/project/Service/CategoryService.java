package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.entity.Category;

public interface CategoryService {
    Category createCategory(String name);
    boolean deleteCategory(String name);
    Category getCategoryByName(String name);
}
