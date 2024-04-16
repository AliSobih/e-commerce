package com.ecomerce.my.ECommerce.project.patterns.StrategyDesignPattern;

import com.ecomerce.my.ECommerce.project.entity.Product;

public class NoDiscountStrategy implements DiscountStrategy{
    @Override
    public double calculateDiscount(Product product) {
        return 0.0;
    }
}
