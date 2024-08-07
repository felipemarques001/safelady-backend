package com.uast.ms_auth.services;

import com.uast.ms_auth.dtos.login.LoginReqDTO;
import com.uast.ms_auth.entities.User;
import com.uast.ms_auth.entities.UserRole;
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

        final String jwtToken = jwtService.generateToken((User) authentication.getPrincipal());
        final UserRole userRole = ((User) authentication.getPrincipal()).getRole();

//        return new LoginResponseDTO(jwtToken, userRole);
        return jwtToken;
    }
}
