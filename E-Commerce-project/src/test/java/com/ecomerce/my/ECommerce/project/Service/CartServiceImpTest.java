package com.ecomerce.my.ECommerce.project.Service;
import com.ecomerce.my.ECommerce.project.dto.CartItemDTO;
import com.ecomerce.my.ECommerce.project.entity.Cart;
import com.ecomerce.my.ECommerce.project.entity.CartItem;
import com.ecomerce.my.ECommerce.project.entity.Product;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.patterns.StrategyDesignPattern.NoDiscountStrategy;
import com.ecomerce.my.ECommerce.project.repository.CartItemRepo;
import com.ecomerce.my.ECommerce.project.repository.CartRepo;
import com.ecomerce.my.ECommerce.project.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CartServiceImpTest {
    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private CartItemRepo cartItemRepo;

    @InjectMocks
    private CartServiceImp cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct_Success() {
        CartItemDTO itemDTO = new CartItemDTO();
        itemDTO.setId(1L);
        itemDTO.setQuantity(2);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100);
        product.setQuantity(5);
        when(productService.getProductById(1L)).thenReturn(product);

        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);
        when(userService.findUser()).thenReturn(user);

        CartItem cartItem = cartService.addProduct(itemDTO);

        assertNotNull(cartItem);
        assertEquals(200, cart.getTotalPrice()); // Assuming no discount is applied
        verify(cartItemRepo, times(1)).save(any(CartItem.class));
        verify(cartRepo, times(1)).save(any(Cart.class));
    }

    @Test
    void testAddProduct_ProductNotFound() {
        CartItemDTO itemDTO = new CartItemDTO();
        itemDTO.setId(1L);
        itemDTO.setQuantity(2);

        when(productService.getProductById(1L)).thenReturn(null);

        CartItem cartItem = cartService.addProduct(itemDTO);

        assertNull(cartItem);
        verify(cartItemRepo, never()).save(any());
        verify(cartRepo, never()).save(any());
    }

    @Test
    void testAddProduct_InsufficientQuantity() {
        CartItemDTO itemDTO = new CartItemDTO();
        itemDTO.setId(1L);
        itemDTO.setQuantity(10);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100);
        product.setQuantity(5);
        when(productService.getProductById(1L)).thenReturn(product);

        CartItem cartItem = cartService.addProduct(itemDTO);

        assertNull(cartItem);
        verify(cartItemRepo, never()).save(any());
        verify(cartRepo, never()).save(any());
    }

    @Test
    void testUpdateCartItem_Success() {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(1L);
        cartItemDTO.setQuantity(3);

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(2);
        when(cartItemRepo.findById(1L)).thenReturn(Optional.of(cartItem));

        CartItem updatedCartItem = cartService.updateCartItem(cartItemDTO);

        assertNotNull(updatedCartItem);
        assertEquals(3, updatedCartItem.getQuantity());
        verify(cartItemRepo, times(1)).save(any(CartItem.class));
    }

    @Test
    void testUpdateCartItem_CartItemNotFound() {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(1L);
        cartItemDTO.setQuantity(3);

        when(cartItemRepo.findById(1L)).thenReturn(Optional.empty());

        CartItem updatedCartItem = cartService.updateCartItem(cartItemDTO);

        assertNull(updatedCartItem);
        verify(cartItemRepo, never()).save(any());
    }
}