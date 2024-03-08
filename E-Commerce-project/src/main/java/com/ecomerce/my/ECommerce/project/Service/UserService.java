package com.ecomerce.my.ECommerce.project.Service;


import com.ecomerce.my.ECommerce.project.dto.UserDTO;
import com.ecomerce.my.ECommerce.project.dto.UserResponse;
import com.ecomerce.my.ECommerce.project.entity.User;

public interface UserService {


	User getUserByEmail(String email);
	User getUser();
	
	User updateUser(UserDTO userDTO);
	
	boolean deleteUser();
}
