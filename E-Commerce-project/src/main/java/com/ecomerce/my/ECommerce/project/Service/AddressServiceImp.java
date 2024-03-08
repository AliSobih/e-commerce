package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.AddressDTO;
import com.ecomerce.my.ECommerce.project.entity.Address;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.repository.AddressRepo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImp implements AddressServer{
    private final AddressRepo addressRepo;
    private final UserService userService;

    @Override
    public boolean createAddress(AddressDTO addressDTO) {
        Address address = addressRepo.findByCountryAndStateAndCityAndStreetAndBuildingName(
                addressDTO.getCountry(),
                addressDTO.getState(),
                addressDTO.getCity(),
                addressDTO.getStreet(),
                addressDTO.getBuildingName()
        ).orElse(null);
        if (address != null) {
            throw new RuntimeException("Address already exists!!!! ");
        }
        try {
            address = Address.builder()
                    .street(addressDTO.getStreet())
                    .buildingName(addressDTO.getBuildingName())
                    .city(addressDTO.getCity())
                    .state(addressDTO.getState())
                    .country(addressDTO.getCountry())
                    .build();
        }catch (Exception e) {
            return false;
        }
        addressRepo.save(address);
        return true;
    }

    @Override
    public List<Address> getAllAddresses() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);
        return user.getAddress();
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepo.findById(id).orElse(null);
    }

    @Override
    public Address updateAddress(AddressDTO addressDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);
        Address address = addressRepo.findById(addressDTO.getAddressId()).orElse(null);
        if (user.getAddress().contains(address)) {
            if (addressDTO.getCity() != null) {
                address.setCity(addressDTO.getCity());
            }
            if (addressDTO.getCountry() != null) {
                address.setCountry(addressDTO.getCountry());
            }
            if (addressDTO.getState() != null) {
                address.setState(addressDTO.getState());
            }
            if (addressDTO.getStreet() != null) {
                address.setStreet(addressDTO.getStreet());
            }
            if (addressDTO.getBuildingName() != null) {
                address.setBuildingName(addressDTO.getBuildingName());
            }
           addressRepo.save(address);
            return address;
        }
        return null;
    }

    @Override
    public boolean deleteAddress(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByEmail(email);
        Address address = addressRepo.findById(id).orElse(null);
        if (user.getAddress().contains(address)) {
            addressRepo.delete(address);
            return true;
        }
        return false;
    }
}
