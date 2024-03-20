package com.ecomerce.my.ECommerce.project.Service;


import com.ecomerce.my.ECommerce.project.dto.AuthenticationRequest;
import com.ecomerce.my.ECommerce.project.dto.AuthenticationResponse;
import com.ecomerce.my.ECommerce.project.dto.RegisterRequest;
import com.ecomerce.my.ECommerce.project.entity.Cart;
import com.ecomerce.my.ECommerce.project.entity.Role;
import com.ecomerce.my.ECommerce.project.entity.Token;
import com.ecomerce.my.ECommerce.project.entity.User;
import com.ecomerce.my.ECommerce.project.repository.CartRepo;
import com.ecomerce.my.ECommerce.project.repository.RoleRepo;
import com.ecomerce.my.ECommerce.project.repository.TokenRepository;
import com.ecomerce.my.ECommerce.project.repository.UserRepo;
import com.ecomerce.my.ECommerce.project.security.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService{
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepo roleRepo;
    private final CartRepo cartRepo;
    private final TokenRepository tokenRepo;

    @Transactional
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = userRepo.findByEmail(request.getEmail()).orElse(null);
        if (user != null) {
            return AuthenticationResponse.builder().token("").build();
        }
        Role role = roleRepo.findByName(request.getRole()).orElseGet(() -> {
            return new Role(request.getRole());
        });
        user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        user.setRole(role);
        String jwtToken = jwtService.generateToken(user);
        Token token = new Token(jwtToken);
        tokenRepo.save(token);
        user.addToken(token);

        if (Objects.equals(request.getRole(), "user")) {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepo.save(cart);
        }
        userRepo.save(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow();
//        todo: if the email or password incorrect then exception will throws
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword(),
                        user.getAuthorities()
                )
        );

        String jwtToken = jwtService.generateToken(user);
        Token token = new Token(jwtToken);
        user.addToken(token);
        tokenRepo.save(token);
        userRepo.save(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
