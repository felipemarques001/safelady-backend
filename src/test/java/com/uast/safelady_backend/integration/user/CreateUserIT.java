package com.uast.safelady_backend.integration.user;

import com.uast.safelady_backend.dtos.user.CreateUserReqDTO;
import com.uast.safelady_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateUserIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private final String VALID_CPF = "71793119015";
    private final String VALID_PHONE_NUMBER = "99999999999";
    private final String VALID_BIRTH_DATE = "2002-12-10";
    private final CreateUserReqDTO USER_REQ_DTO = generateCreateUserDTO(VALID_CPF, VALID_PHONE_NUMBER, VALID_BIRTH_DATE);

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @DisplayName("Given valid user, when POST, then save and return 201")
    @Test
    void givenValidUser_whenPOST_thenSaveAndReturn201() {
        ResponseEntity<Void> response = restTemplate
                .postForEntity("/api/user", USER_REQ_DTO, Void.class);

        System.out.println(response);
        System.out.println(response.getBody());

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, userRepository.findAll().size());
    }

    @DisplayName("Given CPF already in use, when POST, then return message and 400")
    @Test
    void givenCPFInUse_whenPOST_thenReturnMessageAnd400() {
        restTemplate.postForEntity("/api/user", USER_REQ_DTO, Void.class);
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/user", USER_REQ_DTO, Map.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("cpf"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @DisplayName("Given phone number already in use, when POST, then return message and 400")
    @Test
    void givenPhoneNumberInUse_whenPOST_thenReturnMessageAnd400() {
        CreateUserReqDTO userReqDTO2 = generateCreateUserDTO("13354748460", VALID_PHONE_NUMBER, VALID_BIRTH_DATE);

        restTemplate.postForEntity("/api/user", USER_REQ_DTO, Void.class);
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/user", userReqDTO2, Map.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("phoneNumber"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @DisplayName("Given empty fields, when POST, then return messages and 400")
    @Test
    void givenInvalidCpf_whenPOST_thenReturnMessageAnd400() {
        var userReqDTO = new CreateUserReqDTO("", "", "", "", "", "", "", "");

        ResponseEntity<Map> response = restTemplate.postForEntity("/api/user", userReqDTO, Map.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("cpf"));
        assertTrue(response.getBody().containsKey("firstName"));
        assertTrue(response.getBody().containsKey("lastName"));
        assertTrue(response.getBody().containsKey("city"));
        assertTrue(response.getBody().containsKey("street"));
        assertTrue(response.getBody().containsKey("password"));
        assertTrue(response.getBody().containsKey("phoneNumber"));
        assertTrue(response.getBody().containsKey("birthDate"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @DisplayName("Given invalids CPF's, when POST, then return message and 400")
    @Test
    void givenInvalidsCpf_whenPOST_thenReturnMessageAnd400() {
        List<CreateUserReqDTO> userDTOList = List.of(
                generateCreateUserDTO("133.547.484-60", VALID_PHONE_NUMBER, VALID_BIRTH_DATE),
                generateCreateUserDTO("133 547 484 60", VALID_PHONE_NUMBER, VALID_BIRTH_DATE),
                generateCreateUserDTO("133547A48460", VALID_PHONE_NUMBER, VALID_BIRTH_DATE),
                generateCreateUserDTO("133.547.484.60", VALID_PHONE_NUMBER, VALID_BIRTH_DATE)
        );

        List<ResponseEntity<Map>> responseList = new ArrayList<>();

        userDTOList.forEach(userDTO -> {
            responseList.add(restTemplate.postForEntity("/api/user", userDTO, Map.class));
        });

        responseList.forEach(response -> {
            assertNotNull(response);
            assertNotNull(response.getBody());
            assertTrue(response.getBody().containsKey("cpf"));
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        });
    }

    @DisplayName("Given invalids phone numbers, when POST, then return message and 400")
    @Test
    void givenInvalidsPhoneNumbers_whenPOST_thenReturnMessageAnd400() {
        List<CreateUserReqDTO> userDTOList = List.of(
                generateCreateUserDTO(VALID_CPF, "(87) 99999-9999", VALID_BIRTH_DATE),
                generateCreateUserDTO(VALID_CPF, "87 999999999", VALID_BIRTH_DATE),
                generateCreateUserDTO(VALID_CPF, "+558799999999", VALID_BIRTH_DATE),
                generateCreateUserDTO(VALID_CPF, "8791234AB567", VALID_BIRTH_DATE)
        );

        List<ResponseEntity<Map>> responseList = new ArrayList<>();

        userDTOList.forEach(userDTO -> {
            responseList.add(restTemplate.postForEntity("/api/user", userDTO, Map.class));
        });

        responseList.forEach(response -> {
            assertNotNull(response);
            assertNotNull(response.getBody());
            assertTrue(response.getBody().containsKey("phoneNumber"));
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        });
    }

    @DisplayName("Given invalids birth dates, when POST, then return message and 400")
    @Test
    void givenInvalidsBirthDates_whenPOST_thenReturnMessageAnd400() {
        List<CreateUserReqDTO> userDTOList = List.of(
                generateCreateUserDTO(VALID_CPF, VALID_PHONE_NUMBER, "10/12/2004"),
                generateCreateUserDTO(VALID_CPF, VALID_PHONE_NUMBER, "10-12-2004"),
                generateCreateUserDTO(VALID_CPF, VALID_PHONE_NUMBER, "10 12 2004"),
                generateCreateUserDTO(VALID_CPF, VALID_PHONE_NUMBER, "10a12a2004")
        );

        List<ResponseEntity<Map>> responseList = new ArrayList<>();

        userDTOList.forEach(userDTO -> {
            responseList.add(restTemplate.postForEntity("/api/user", userDTO, Map.class));
        });

        responseList.forEach(response -> {
            assertNotNull(response);
            assertNotNull(response.getBody());
            assertTrue(response.getBody().containsKey("birthDate"));
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        });
    }

    private CreateUserReqDTO generateCreateUserDTO(String cpf, String phoneNumber, String birthDate) {
        return new CreateUserReqDTO(
                cpf,
                "Felipe",
                "Rocha",
                "Tabira",
                "Rua 2",
                "123",
                phoneNumber,
                birthDate
        );
    }
}

