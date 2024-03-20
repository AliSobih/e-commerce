package com.ecomerce.my.ECommerce.project.rest;

import com.ecomerce.my.ECommerce.project.Service.CategoryService;
import com.ecomerce.my.ECommerce.project.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class CategoryRest {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestParam String name) {
        return name.trim().isEmpty() || categoryService.createCategory(name) == null ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST):
                new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteCategoryByName(@RequestParam String name) {
        return categoryService.deleteCategory(name) && !name.trim().isEmpty()?
                new ResponseEntity<>(HttpStatus.NO_CONTENT):
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
