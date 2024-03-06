package com.ecomerce.my.ECommerce.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, message = "Product name must contain at least 3 characters")
    private String name = "";

    @NotBlank
    @Size(min = 6, message = "Product description must contain at least 6 characters")
    private String description;

    private String image = "";

    private Integer quantity = 0;

    private double price = 0.0;

    private double discount = 0.0;

    @OneToMany(mappedBy = "product")
    List<CartItem> cartItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
    public Product(String name, String description, String image, Integer quantity, double price, double discount) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    //    Convenience  method
    public void addCartItem(CartItem item) {
        item.setProduct(this);
        cartItems.add(item);
    }

    public void addOrderItem(OrderItem item) {
        item.setProduct(this);
        orderItems.add(item);
    }
}
