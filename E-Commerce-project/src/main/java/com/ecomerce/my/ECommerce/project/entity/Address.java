package com.ecomerce.my.ECommerce.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@NotBlank
	@Size(min = 5, message = "Street name must contain at least 5 characters")
	private String street;
	
	@NotBlank
	@Size(min = 3, message = "Building name must contain at least 3 characters")
	private String buildingName;
	
	@NotBlank
	@Size(min = 4, message = "City name must contain at least 4 characters")
	private String city;
	
	@NotBlank
	@Size(min = 2, message = "State name must contain at least 2 characters")
	private String state;
	
	@NotBlank
	@Size(min = 2, message = "Country name must contain at least 2 characters")
	private String country;

	@ManyToMany
	@JoinTable(name = "user_address", inverseJoinColumns = @JoinColumn(name = "user_id"), joinColumns = @JoinColumn(name = "address_id"))
	private List<User> users = new ArrayList<>();

	public Address(String country, String state, String city, String street, String buildingName) {
		this.country = country;
		this.state = state;
		this.city = city;
		this.street = street;
		this.buildingName = buildingName;
	}
}
