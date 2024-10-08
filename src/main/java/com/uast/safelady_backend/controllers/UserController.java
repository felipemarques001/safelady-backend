package com.uast.safelady_backend.controllers;

import com.uast.safelady_backend.dtos.user.CreateUserReqDTO;
import com.uast.safelady_backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserReqDTO data) {
        userService.saveUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
