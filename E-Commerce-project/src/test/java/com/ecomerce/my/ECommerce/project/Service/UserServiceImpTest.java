package com.ecomerce.my.ECommerce.project.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
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

    @InjectMocks
    private UserServiceImp userService;

    @Test
    void testGetUserByEmail_Found() {
        User expectedUser = new User();
        given(userRepo.findByEmail("test@example.com")).willReturn(Optional.of(expectedUser));

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
}