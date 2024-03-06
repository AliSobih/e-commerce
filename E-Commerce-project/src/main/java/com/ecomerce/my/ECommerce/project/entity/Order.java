package com.ecomerce.my.ECommerce.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email = "";

    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE , CascadeType.DETACH, CascadeType.REFRESH})
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDate orderDate;

    private Double totalAmount = 0.0;

    private String orderStatus;

    //    Convenience  method
    public void addItem(OrderItem item) {
        item.setOrder(this);
        orderItems.add(item);
    }
}
