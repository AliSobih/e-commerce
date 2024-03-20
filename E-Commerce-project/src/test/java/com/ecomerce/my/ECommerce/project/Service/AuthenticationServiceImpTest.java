package com.ecomerce.my.ECommerce.project.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecomerce.my.ECommerce.project.dto.AuthenticationRequest;
import com.ecomerce.my.ECommerce.project.dto.AuthenticationResponse;
import com.ecomerce.my.ECommerce.project.dto.RegisterRequest;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.repository.CartRepo;
import com.ecomerce.my.ECommerce.project.repository.RoleRepo;
import com.ecomerce.my.ECommerce.project.repository.TokenRepository;
import com.ecomerce.my.ECommerce.project.repository.UserRepo;
import com.ecomerce.my.ECommerce.project.security.jwt.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImpTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RoleRepo roleRepo;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private TokenRepository tokenRepo;

    @InjectMocks
    private AuthenticationServiceImp authenticationService;


    @Test
    void testRegister_UserExists() {
        // Mock behavior for userRepo.findByEmail
        when(userRepo.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@example.com");

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("", response.getToken());
    }

    @Test
    void testRegister_NewUser() {
        when(jwtService.generateToken(any())).thenReturn("mocked-jwt-token");
        // Mock behavior for userRepo.findByEmail
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        RegisterRequest request = new RegisterRequest();
        request.setEmail("new@example.com");
        request.setRole("user");

        AuthenticationResponse response = authenticationService.register(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        // Verify that appropriate methods are called
        verify(userRepo, times(1)).save(any());
        verify(jwtService, times(1)).generateToken(any());
        verify(tokenRepo, times(1)).save(any());
        verify(cartRepo, times(1)).save(any());
    }

    @Test
    void testLogin_ValidCredentials() {
        // Mock behavior for userRepo.findByEmail
        when(userRepo.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("nonexistent@example.com");
        request.setPassword("invalid-password");

        assertThrows(NoSuchElementException.class, () -> authenticationService.login(request));
        // Verify that tokenRepo.save() is not called
        verify(tokenRepo, never()).save(any());
    }

    @Test
    void testLogin_InvalidCredentials() {
        // Mock behavior for userRepo.findByEmail
        when(userRepo.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("nonexistent@example.com");
        request.setPassword("invalid-password");

        assertThrows(NoSuchElementException.class, () -> authenticationService.login(request));
        // Verify that tokenRepo.save() is not called
        verify(tokenRepo, never()).save(any());
    }
}
