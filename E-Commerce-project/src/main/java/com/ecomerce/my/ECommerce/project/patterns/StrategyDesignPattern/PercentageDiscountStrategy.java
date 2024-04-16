package com.ecomerce.my.ECommerce.project.patterns.StrategyDesignPattern;

import com.ecomerce.my.ECommerce.project.entity.Product;

public class PercentageDiscountStrategy implements DiscountStrategy{
    @Override
    public double calculateDiscount(Product product) {
        return product.getPrice() * product.getDiscount() / 100.0;
    }
}
