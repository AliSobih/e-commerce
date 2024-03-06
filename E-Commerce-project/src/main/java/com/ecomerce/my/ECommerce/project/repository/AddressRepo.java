package com.ecomerce.my.ECommerce.project.repository;


import com.ecomerce.my.ECommerce.project.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

//	Address findByCountryAndStateAndCityAndStreetAndBuildingName
//			(String country, String state, String city, String street, String buildingName);

}
