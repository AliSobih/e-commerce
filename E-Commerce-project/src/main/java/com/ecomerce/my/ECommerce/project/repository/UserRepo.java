package com.ecomerce.my.ECommerce.project.repository;


import com.ecomerce.my.ECommerce.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
//	@Query("SELECT u FROM User u JOIN FETCH u.addresses a WHERE a.addressId = ?1")
//	List<User> findByAddress(Long addressId);
//
	Optional<User> findByEmail(String email);
}
