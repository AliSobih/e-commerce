package com.ecomerce.my.ECommerce.project.rest;

import com.ecomerce.my.ECommerce.project.Service.CartService;
import com.ecomerce.my.ECommerce.project.Service.ProductService;
import com.ecomerce.my.ECommerce.project.dto.CartItemDTO;
import com.ecomerce.my.ECommerce.project.entity.CartItem;
import com.ecomerce.my.ECommerce.project.repository.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/cart")
@RequiredArgsConstructor
public class CartRest {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<Void> addProductToCart(@RequestBody CartItemDTO cartItemDTO) {
        CartItem item = cartService.addProduct(cartItemDTO);
        return item != null ? new ResponseEntity<>(HttpStatus.CREATED): new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/update-item")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItemDTO cartItemDTO) {
        CartItem cartItem = cartService.updateCartItem(cartItemDTO);
        return cartItem == null ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
}
