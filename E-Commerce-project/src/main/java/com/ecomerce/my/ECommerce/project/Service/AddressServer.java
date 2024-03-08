package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.AddressDTO;
import com.ecomerce.my.ECommerce.project.entity.Address;

import java.util.List;

public interface AddressServer {
    boolean createAddress(AddressDTO addressDTO);
    List<Address> getAllAddresses();
    Address getAddressById(Long id);
    Address updateAddress(AddressDTO addressDTO);
   boolean deleteAddress(Long id);
}
