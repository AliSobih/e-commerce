package com.ecomerce.my.ECommerce.project.repository;


import com.ecomerce.my.ECommerce.project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

//	Category findByCategoryName(String categoryName);

}
