package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.CartItemDTO;
import com.ecomerce.my.ECommerce.project.entity.CartItem;
import com.ecomerce.my.ECommerce.project.entity.Product;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.repository.CartItemRepo;
import com.ecomerce.my.ECommerce.project.repository.CartRepo;
import com.ecomerce.my.ECommerce.project.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {
    private final ProductService productService;
    private final ProductRepo productRepo;
    private final UserService userService;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    @Override
    @Transactional
    public CartItem addProduct(CartItemDTO item) {
        Product product = productService.getProductById(item.getId());
        if (product == null || item.getQuantity() <= 0) {
            return null;
        }
        if (product.getQuantity() < item.getQuantity()) {
            return null;
        }
        CartItem cartItem = new CartItem();
        User user = userService.findUser();
        cartItem.setCart(user.getCart());
        cartItem.setQuantity(item.getQuantity());
        cartItem.setDiscount(product.getDiscount());
        cartItemRepo.save(cartItem);
        product.addCartItem(cartItem);
        productRepo.save(product);
//        System.out.println((product.getPrice() - product.getDiscount()) * item.getQuantity());
        user.getCart().addToTotalPrice((product.getPrice() - product.getDiscount()) * item.getQuantity());
        cartRepo.save(user.getCart());
        return cartItem;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    @Override
    public CartItem updateProduct(long id) {
        return null;
    }
}
