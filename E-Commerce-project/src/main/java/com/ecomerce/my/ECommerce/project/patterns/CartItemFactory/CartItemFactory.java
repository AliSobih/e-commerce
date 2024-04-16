package com.ecomerce.my.ECommerce.project.patterns.CartItemFactory;

import com.ecomerce.my.ECommerce.project.entity.CartItem;
import com.ecomerce.my.ECommerce.project.entity.Product;

public class CartItemFactory {
    public static CartItem createCartItem(Product product, int quantity, double discount) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setDiscount(discount);
        return cartItem;
    }
}
