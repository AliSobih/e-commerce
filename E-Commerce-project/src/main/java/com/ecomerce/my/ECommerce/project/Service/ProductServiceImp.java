package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.ProductDTO;
import com.ecomerce.my.ECommerce.project.entity.Category;
import com.ecomerce.my.ECommerce.project.entity.Product;
import com.ecomerce.my.ECommerce.project.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{
    private final ProductRepo productRepo;
    private final CategoryService categoryService;
    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        if (productDTO.getName() == null  || productDTO.getDescription() == null) {
            return null;
        }
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());

        if (productDTO.getImageUrl() != null) {
            product.setImage(productDTO.getImageUrl());
        }

        if (productDTO.getQuantity() <= 1) {
            product.setQuantity(productDTO.getQuantity());
        }

        if (productDTO.getPrice() <= 1) {
            product.setPrice(productDTO.getPrice());
        }

        if (productDTO.getDiscount() <= 1) {
            product.setDiscount(productDTO.getDiscount());
        }
        if (productDTO.getCategoryName() != null) {
            Category category = categoryService.getCategoryByName(productDTO.getCategoryName());
            if (category != null)  {
                product.setCategory(category);
                category.addProduct(product);
            }
        }
        return productRepo.save(product);
    }

    @Override
    public boolean deleteProduct(long id) {
        boolean productExists = productRepo.existsById(id);
        productRepo.deleteById(id);
        return  productExists;
    }

    @Override
    public Product updateProduct(ProductDTO productDTO) {
        Product product = this.getProductById(productDTO.getId());
        if (productDTO == null) {
            return null;
        }
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getImageUrl() != null) {
            product.setImage(productDTO.getImageUrl());
        }
        if (productDTO.getQuantity() <= 1) {
            product.setQuantity(productDTO.getQuantity());
        }
        if (productDTO.getPrice() <= 1) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getDiscount() <= 1) {
            product.setDiscount(productDTO.getDiscount());
        }
        if (productDTO.getCategoryName() != null) {
            Category category = categoryService.getCategoryByName(productDTO.getCategoryName());
            if (category != null)  {
                product.setCategory(category);
                category.addProduct(product);
            }
        }
        return product;
    }

    @Override
    public Product getProductById(long id) {
        return productRepo.findById(id).orElse(null);
    }

    @Override
    public List<Product> getProducts() {
        return productRepo.findAll();
    }
}
