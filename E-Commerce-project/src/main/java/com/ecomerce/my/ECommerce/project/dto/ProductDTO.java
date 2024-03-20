package com.ecomerce.my.ECommerce.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private  String imageUrl;
    private int quantity;
    private double price;
    private double discount;
    private String categoryName;
}
