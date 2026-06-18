package com.petshop.service;

import com.petshop.dto.request.AdminLoginRequest;
import com.petshop.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(AdminLoginRequest request);
}
