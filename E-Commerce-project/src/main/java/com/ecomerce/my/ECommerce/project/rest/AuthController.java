package com.ecomerce.my.ECommerce.project.rest;

import com.ecomerce.my.ECommerce.project.Service.AuthenticationServiceImp;
import com.ecomerce.my.ECommerce.project.dto.AuthenticationRequest;
import com.ecomerce.my.ECommerce.project.dto.AuthenticationResponse;
import com.ecomerce.my.ECommerce.project.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationServiceImp authenticationServiceImp;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

        return new ResponseEntity<>(authenticationServiceImp.register(request), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(authenticationServiceImp.login(request));
    }
}
