package com.ecomerce.my.ECommerce.project.repository;


import com.ecomerce.my.ECommerce.project.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

//	Page<Product> findByProductNameLike(String keyword, Pageable pageDetails);

}
