package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.ProductDTO;
import com.ecomerce.my.ECommerce.project.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);
    boolean deleteProduct(long id);
    Product updateProduct(ProductDTO productDTO);
    Product getProductById(long id);
    List<Product> getProducts();
}
