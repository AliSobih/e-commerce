package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.CartItemDTO;
import com.ecomerce.my.ECommerce.project.entity.Cart;
import com.ecomerce.my.ECommerce.project.entity.CartItem;
import com.ecomerce.my.ECommerce.project.entity.Product;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.patterns.CartItemFactory.CartItemFactory;
import com.ecomerce.my.ECommerce.project.patterns.StrategyDesignPattern.DiscountContext;
import com.ecomerce.my.ECommerce.project.patterns.StrategyDesignPattern.DiscountStrategy;
import com.ecomerce.my.ECommerce.project.patterns.StrategyDesignPattern.NoDiscountStrategy;
import com.ecomerce.my.ECommerce.project.patterns.StrategyDesignPattern.PercentageDiscountStrategy;
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
        if (product == null || item.getQuantity() <= 0 || product.getQuantity() < item.getQuantity()) {
            return null;
        }

        User user = userService.findUser();

        DiscountStrategy discountStrategy = null;
        if (product.getPrice() * item.getQuantity() > 1000) {
            discountStrategy = new PercentageDiscountStrategy();
        }else {
            discountStrategy = new NoDiscountStrategy();
        }

        Cart cart = user.getCart();
        DiscountContext discountContext = new DiscountContext(discountStrategy);

        cart.addToTotalPrice((product.getPrice() - discountContext.calculateDiscount(product)) * item.getQuantity());

        CartItem cartItem = CartItemFactory.createCartItem(product, item.getQuantity(), product.getDiscount());
        cartItemRepo.save(cartItem);
        cart.addItem(cartItem);

        cartRepo.save(cart);
        return cartItem;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }

    @Override
    public CartItem updateCartItem(CartItemDTO cartItemDTO) {
//        userService.findUser()
        CartItem cartItem = cartItemRepo.findById(cartItemDTO.getId()).orElse(null);
        if (cartItem == null || cartItemDTO.getQuantity() <= 0) {
            return null;
        }
        cartItem.setQuantity(cartItemDTO.getQuantity());
        cartItemRepo.save(cartItem);
        return cartItem;
    }
}
