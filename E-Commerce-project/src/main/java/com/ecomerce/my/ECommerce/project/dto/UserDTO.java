package com.ecomerce.my.ECommerce.project.dto;

import com.ecomerce.my.ECommerce.project.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private String firstName;
	private String lastName;
	private String mobileNumber;
}
