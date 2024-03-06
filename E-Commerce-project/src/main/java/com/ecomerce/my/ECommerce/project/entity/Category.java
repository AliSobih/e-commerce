package com.ecomerce.my.ECommerce.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, message = "Category name must contain at least 3 characters")
    private String name;

    @OneToMany(mappedBy = "category", cascade =  CascadeType.ALL )
    private List<Product> products = new ArrayList<>();

    //    Convenience  method
    public void addItem(Product item) {
        item.setCategory(this);
        products.add(item);
    }
}
