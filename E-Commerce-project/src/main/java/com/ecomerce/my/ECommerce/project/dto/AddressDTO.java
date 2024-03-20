package com.ecomerce.my.ECommerce.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
	private Long addressId;
	private String street;
	private String buildingName;
	private String city;
	private String state;
	private String country;

	public AddressDTO(String country,
					  String state,
					  String city,
					  String street,
					  String buildingName) {
		this.street = street;
		this.buildingName = buildingName;
		this.city = city;
		this.state = state;
		this.country = country;
	}
}
