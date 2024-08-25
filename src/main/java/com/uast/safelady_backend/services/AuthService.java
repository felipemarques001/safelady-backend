package com.uast.safelady_backend.services;

import com.uast.safelady_backend.dtos.login.LoginReqDTO;
import com.uast.safelady_backend.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(JWTService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String loginUser(LoginReqDTO requestDTO) {
        var userToken = new UsernamePasswordAuthenticationToken(requestDTO.cpf(), requestDTO.password());
        Authentication authentication = authenticationManager.authenticate(userToken);

        // Returns the authenticated user's JWT token
        return jwtService.generateToken((User) authentication.getPrincipal());
    }
}
