package com.ecomerce.my.ECommerce.project.patterns.StrategyDesignPattern;

import com.ecomerce.my.ECommerce.project.entity.Product;

public class DiscountContext {
    private final DiscountStrategy strategy;

    public DiscountContext(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateDiscount(Product product) {
        return strategy.calculateDiscount(product);
    }
}
