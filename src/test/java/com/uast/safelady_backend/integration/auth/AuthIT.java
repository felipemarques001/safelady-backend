package com.uast.safelady_backend.integration.auth;

import com.uast.safelady_backend.dtos.login.LoginReqDTO;
import com.uast.safelady_backend.dtos.login.LoginResDTO;
import com.uast.safelady_backend.entities.User;
import com.uast.safelady_backend.entities.UserRole;
import com.uast.safelady_backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String VALID_CPF = "71793119015";
    private final String VALID_PASSWORD = "123";

    @BeforeEach
    void setUp() {
        var user = new User(
                UUID.randomUUID(),
                VALID_CPF,
                "Felipe",
                "Rocha",
                "São Paulo",
                "Rua 1",
                passwordEncoder.encode(VALID_PASSWORD),
                LocalDate.now(),
                "9999999999",
                UserRole.USER
        );
        userRepository.save(user);
    }

    @AfterEach
    void setEnd() {
        userRepository.deleteAll();
    }

    @DisplayName("Given valid user, when login, then return JWT and 200")
    @Test
    void givenValidUser_whenLogin_thenReturnJWTAnd200() {
        var loginReqDTO = new LoginReqDTO(VALID_CPF, VALID_PASSWORD);

        ResponseEntity<LoginResDTO> response = restTemplate
                .postForEntity("/api/auth/login", loginReqDTO, LoginResDTO.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().jwtToken());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("Given invalid data, when login, then return 403")
    @Test
    void givenInvalidData_whenLogin_thenReturn403() {
        var loginReqDTO = new LoginReqDTO("31683975049", "invalidPassword");

        ResponseEntity<LoginResDTO> response = restTemplate
                .postForEntity("/api/auth/login", loginReqDTO, LoginResDTO.class);

        assertNotNull(response);
        assertNull(response.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
