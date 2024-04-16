package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.ProductDTO;
import com.ecomerce.my.ECommerce.project.entity.Category;
import com.ecomerce.my.ECommerce.project.entity.Product;
import com.ecomerce.my.ECommerce.project.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceImpTest {


    @Mock
    private ProductRepo productRepo;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductServiceImp productService;

    public ProductServiceImpTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct_Success() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");

        when(categoryService.getCategoryByName(any())).thenReturn(new Category());
        when(productRepo.save(any())).thenReturn(new Product());

        // Act
        Product createdProduct = productService.createProduct(productDTO);

        // Assert
        assertNotNull(createdProduct);
        verify(productRepo, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateProduct_NullInput() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();

        // Act
        Product createdProduct = productService.createProduct(productDTO);

        // Assert
        assertNull(createdProduct);
        verify(productRepo, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        long productId = 1L;
        when(productRepo.existsById(productId)).thenReturn(true);

        // Act
        boolean result = productService.deleteProduct(productId);

        // Assert
        assertTrue(result);
        verify(productRepo, times(1)).deleteById(productId);
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Updated Product");

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        when(productRepo.findById(productDTO.getId())).thenReturn(Optional.of(existingProduct));
        when(categoryService.getCategoryByName(any())).thenReturn(new Category());

        // Act
        Product updatedProduct = productService.updateProduct(productDTO);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals(productDTO.getName(), updatedProduct.getName());
    }

    @Test
    void testGetProductById_Success() {
        // Arrange
        long productId = 1L;
        Product expectedProduct = new Product();
        when(productRepo.findById(productId)).thenReturn(Optional.of(expectedProduct));

        // Act
        Product retrievedProduct = productService.getProductById(productId);

        // Assert
        assertNotNull(retrievedProduct);
        assertEquals(expectedProduct, retrievedProduct);
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange
        long productId = 1L;
        when(productRepo.findById(productId)).thenReturn(Optional.empty());

        // Act
        Product retrievedProduct = productService.getProductById(productId);

        // Assert
        assertNull(retrievedProduct);
    }

    @Test
    void testGetProducts() {
        // Arrange
        List<Product> expectedProducts = Collections.singletonList(new Product());
        when(productRepo.findAll()).thenReturn(expectedProducts);

        // Act
        List<Product> retrievedProducts = productService.getProducts();

        // Assert
        assertEquals(expectedProducts, retrievedProducts);
    }
}