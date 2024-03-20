package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.AddressDTO;
import com.ecomerce.my.ECommerce.project.entity.Address;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.repository.AddressRepo;
import com.ecomerce.my.ECommerce.project.repository.UserRepo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImp implements AddressServer{
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    private final UserService userService;

    @Override
    @Transactional
    public boolean createAddress(AddressDTO addressDTO) {
        Address address = addressRepo.findByCountryAndStateAndCityAndStreetAndBuildingName(
                addressDTO.getCountry().toLowerCase(),
                addressDTO.getState().toLowerCase(),
                addressDTO.getCity().toLowerCase(),
                addressDTO.getStreet().toLowerCase(),
                addressDTO.getBuildingName().toLowerCase()
        ).orElse(null);
        if (address != null) {
            throw new RuntimeException("Address already exists!!!! ");
        }
        try {
            address = Address.builder()
                    .street(addressDTO.getStreet().toLowerCase())
                    .buildingName(addressDTO.getBuildingName().toLowerCase())
                    .city(addressDTO.getCity().toLowerCase())
                    .state(addressDTO.getState().toLowerCase())
                    .country(addressDTO.getCountry().toLowerCase())
                    .build();
        }catch (Exception e) {
            return false;
        }
        addressRepo.save(address);
        User user = userService.getUser();
        user.addAddress(address);
        userRepo.save(user);
        return true;
    }

    @Override
    public List<Address> getAllAddresses() {
        User user = userService.getUser();
        return user.getAddresses();
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
        if (user.getAddresses().contains(address)) {
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
        if (user.getAddresses().contains(address)) {
            addressRepo.delete(address);
            return true;
        }
        return false;
    }
}
