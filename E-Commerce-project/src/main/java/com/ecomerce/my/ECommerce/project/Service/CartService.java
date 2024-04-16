package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.CartItemDTO;
import com.ecomerce.my.ECommerce.project.entity.CartItem;

public interface CartService {
    CartItem addProduct(CartItemDTO cart);
    boolean deleteProduct(Long id);
    CartItem updateCartItem(CartItemDTO cartItemDTO);
}
