package com.petshop.service.impl;

import com.petshop.dto.request.AdminLoginRequest;
import com.petshop.dto.response.LoginResponse;
import com.petshop.entity.Admin;
import com.petshop.exception.AuthenticationException;
import com.petshop.repository.AdminRepository;
import com.petshop.service.AuthService;
import com.petshop.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AdminRepository adminRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(AdminLoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            Admin admin = adminRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new AuthenticationException("Admin not found"));

            String token = jwtUtil.generateToken(admin.getUsername());

            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setType("Bearer");
            response.setAdminId(admin.getId());
            response.setUsername(admin.getUsername());
            response.setRole(admin.getRole().name());

            return response;
        } catch (Exception e) {
            throw new AuthenticationException("Invalid username or password");
        }
    }
}
