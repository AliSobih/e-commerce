package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.AddressDTO;
import com.ecomerce.my.ECommerce.project.entity.Address;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.repository.AddressRepo;
import com.ecomerce.my.ECommerce.project.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImpTest {

    @Mock
    private AddressRepo addressRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserService userService;

    @InjectMocks
    private AddressServiceImp addressService;

    @Test
    void testCreateAddress_AddressDoesNotExist() {
        AddressDTO addressDTO = new AddressDTO("Country", "State", "City", "Street", "Building");

        User user = new User();
        when(userService.getUser()).thenReturn(user);

        when(addressRepo.findByCountryAndStateAndCityAndStreetAndBuildingName(
                addressDTO.getCountry().toLowerCase(),
                addressDTO.getState().toLowerCase(),
                addressDTO.getCity().toLowerCase(),
                addressDTO.getStreet().toLowerCase(),
                addressDTO.getBuildingName().toLowerCase()))
                .thenReturn(Optional.empty());

        assertTrue(addressService.createAddress(addressDTO));
        verify(addressRepo, times(1)).save(any());
        verify(userRepo, times(1)).save(any());
    }

    @Test
    void testCreateAddress_AddressExists() {
        AddressDTO addressDTO = new AddressDTO("Country",
                "State", "City",
                "Street", "Building");

        when(addressRepo.findByCountryAndStateAndCityAndStreetAndBuildingName(
                addressDTO.getCountry().toLowerCase(),
                addressDTO.getState().toLowerCase(),
                addressDTO.getCity().toLowerCase(),
                addressDTO.getStreet().toLowerCase(),
                addressDTO.getBuildingName().toLowerCase()))
                .thenReturn(Optional.of(new Address()));

        assertThrows(RuntimeException.class, () -> addressService.createAddress(addressDTO));
        verify(addressRepo, never()).save(any());
        verify(userRepo, never()).save(any());
    }

    @Test
    void testGetAllAddresses() {
        User user = new User();
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        user.setAddresses(addresses);

        when(userService.getUser()).thenReturn(user);

        assertEquals(addresses, addressService.getAllAddresses());
    }

    @Test
    void testGetAddressById_AddressExists() {
        Address address = new Address();
        address.setId(1L);

        when(addressRepo.findById(1L)).thenReturn(Optional.of(address));

        assertEquals(address, addressService.getAddressById(1L));
    }

    @Test
    void testGetAddressById_AddressDoesNotExist() {
        when(addressRepo.findById(1L)).thenReturn(Optional.empty());

        assertNull(addressService.getAddressById(1L));
    }

    @Test
    void testUpdateAddress_AddressExistsAndBelongsToUser() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(1L);
        addressDTO.setCity("New City");

        String email = "test@example.com";
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(email);
        User user = new User();
        user.setEmail(email);
        when(userService.getUserByEmail(email)).thenReturn(user);

        Address address = new Address();
        address.setId(1L);
        user.addAddress(address);
        when(addressRepo.findById(1L)).thenReturn(Optional.of(address));

        assertEquals("New City", addressService.updateAddress(addressDTO).getCity());
        verify(addressRepo, times(1)).save(any());
    }

    @Test
    void testUpdateAddress_AddressExistsButDoesNotBelongToUser() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressId(1L);
        addressDTO.setCity("New City");

        String email = "test@example.com";
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(email);
        User user = new User();
        user.setEmail(email);
        when(userService.getUserByEmail(email)).thenReturn(user);

        Address address = new Address(); // Address not associated with the user
        address.setId(1L);
        when(addressRepo.findById(1L)).thenReturn(Optional.of(address));

        assertNull(addressService.updateAddress(addressDTO));
        verify(addressRepo, never()).save(any());
    }

    @Test
    void testDeleteAddress_AddressExistsAndBelongsToUser() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Address address = new Address();
        address.setId(1L);

        String email = "test@example.com";
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(email);
        User user = new User();
        user.setEmail(email);
        user.addAddress(address);
        when(userService.getUserByEmail(email)).thenReturn(user);

        when(addressRepo.findById(1L)).thenReturn(Optional.of(address));

        assertTrue(addressService.deleteAddress(1L));
        verify(addressRepo, times(1)).delete(address);
    }

    @Test
    void testDeleteAddress_AddressExistsButDoesNotBelongToUser() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Address address = new Address();
        address.setId(1L);

        String email = "test@example.com";
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(email);
        User user = new User(); // User has no addresses
        user.setEmail(email);
        when(userService.getUserByEmail(email)).thenReturn(user);

        when(addressRepo.findById(1L)).thenReturn(Optional.of(address));

        assertFalse(addressService.deleteAddress(1L));
        verify(addressRepo, never()).delete(any());
    }

    @Test
    void testDeleteAddress_AddressDoesNotExist() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        String email = "test@example.com";
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(email);
        User user = new User();
        user.setEmail(email);
        when(userService.getUserByEmail(email)).thenReturn(user);

        when(addressRepo.findById(1L)).thenReturn(Optional.empty());

        assertFalse(addressService.deleteAddress(1L));
        verify(addressRepo, never()).delete(any());
    }
}