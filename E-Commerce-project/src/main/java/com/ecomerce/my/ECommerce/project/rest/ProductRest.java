package com.ecomerce.my.ECommerce.project.rest;

import com.ecomerce.my.ECommerce.project.Service.ProductService;
import com.ecomerce.my.ECommerce.project.dto.ProductDTO;
import com.ecomerce.my.ECommerce.project.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller/product")
@RequiredArgsConstructor
public class ProductRest {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> AddNewProduct(@RequestBody ProductDTO productDTO) {
        Product product = productService.createProduct(productDTO);
        return product != null ? new ResponseEntity<>(product, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
