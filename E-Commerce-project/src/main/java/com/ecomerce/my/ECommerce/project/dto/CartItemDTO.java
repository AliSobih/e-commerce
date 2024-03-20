package com.ecomerce.my.ECommerce.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private int quantity;
}
