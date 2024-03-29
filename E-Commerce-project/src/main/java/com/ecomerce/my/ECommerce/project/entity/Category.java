package com.ecomerce.my.ECommerce.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, message = "Category name must contain at least 3 characters")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade =  CascadeType.ALL )
    private List<Product> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    //    Convenience  method
    public void addProduct(Product item) {
        item.setCategory(this);
        products.add(item);
    }
}
