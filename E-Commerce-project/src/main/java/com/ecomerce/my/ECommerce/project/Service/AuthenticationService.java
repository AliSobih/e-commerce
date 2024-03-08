package com.ecomerce.my.ECommerce.project.Service;

import com.ecomerce.my.ECommerce.project.dto.AuthenticationRequest;
import com.ecomerce.my.ECommerce.project.dto.AuthenticationResponse;
import com.ecomerce.my.ECommerce.project.dto.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
}
