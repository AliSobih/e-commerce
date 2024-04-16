package com.ecomerce.my.ECommerce.project.patterns.StrategyDesignPattern;

import com.ecomerce.my.ECommerce.project.entity.Product;

public interface DiscountStrategy {
    double calculateDiscount(Product product);
}
