package com.ecomerce.my.ECommerce.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", orphanRemoval = true)
    @JsonIgnore
    private List<CartItem> cartItems =new ArrayList<>();

    @Column(name = "total_price")
    private Double totalPrice = 0.0;

//    Convenience  method
    public void addItem(CartItem item) {
        item.setCart(this);
        cartItems.add(item);
    }
}
