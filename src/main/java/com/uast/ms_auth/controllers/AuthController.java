package com.uast.ms_auth.controllers;

import com.uast.ms_auth.dtos.login.LoginReqDTO;
import com.uast.ms_auth.dtos.login.LoginResDTO;
import com.uast.ms_auth.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> login(@RequestBody @Valid LoginReqDTO data) {
        String jwtToken = authService.loginUser(data);
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResDTO(jwtToken));
    }
}
