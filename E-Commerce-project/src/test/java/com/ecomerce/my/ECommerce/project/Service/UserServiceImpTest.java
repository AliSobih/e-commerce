package com.ecomerce.my.ECommerce.project.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecomerce.my.ECommerce.project.dto.UserDTO;
import com.ecomerce.my.ECommerce.project.entity.Cart;
import com.ecomerce.my.ECommerce.project.entity.User;
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

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserServiceImp userService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock behavior for authentication.getName()
        when(authentication.getName()).thenReturn("test@example.com");
    }

    @Test
    void testGetUserByEmail_Found() {
        // Mock behavior for userRepo.findByEmail
        User expectedUser = new User();
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(expectedUser));

        User result = userService.getUserByEmail("test@example.com");

        assertNotNull(result);
        assertSame(expectedUser, result);
    }

    @Test
    void testGetUserByEmail_NotFound() {
        // Mock behavior for userRepo.findByEmail
        when(userRepo.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        User result = userService.getUserByEmail("unknown@example.com");

        assertNull(result);
    }

    @Test
    void testUpdateUser() {
        // Mock behavior for findUser
        User user = new User();
        when(userService.findUser()).thenReturn(user);

        // Test user DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("John");

        // Mock behavior for userRepo.findByEmail
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(user));

        // Call updateUser
        User updatedUser = userService.updateUser(userDTO);

        assertNotNull(updatedUser);
        assertEquals("John", updatedUser.getFirstName());
        // Verify that save method is called
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        // Mock behavior for findUser
        User user = new User();
        when(userService.findUser()).thenReturn(user);

        // Call deleteUser
        boolean result = userService.deleteUser();

        assertTrue(result);
        // Verify that delete method is called
        verify(userRepo, times(1)).delete(user);
    }

    @Test
    void testGetCart() {
        // Mock behavior for findUser
        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);
        when(userService.findUser()).thenReturn(user);

        // Call getCart
        Cart result = userService.getCart();

        assertNotNull(result);
        assertSame(cart, result);
    }

    @Test
    void testFindUser() {
        // Mock behavior for userRepo.findByEmail
        User expectedUser = new User();
        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(expectedUser));

        // Call findUser
        User result = userService.findUser();

        assertNotNull(result);
        assertSame(expectedUser, result);
    }
}