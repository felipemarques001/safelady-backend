package com.uast.ms_auth.services;

import com.uast.ms_auth.dtos.user.CreateUserReqDTO;
import com.uast.ms_auth.entities.User;
import com.uast.ms_auth.entities.UserRole;
import com.uast.ms_auth.exceptions.FieldAlreadyInUseException;
import com.uast.ms_auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveUser(CreateUserReqDTO reqDTO) {
        if(userRepository.findByCpf(reqDTO.cpf()) != null) {
            throw new FieldAlreadyInUseException("CPF", reqDTO.cpf());
        }

        if(userRepository.findByPhoneNumber(reqDTO.phoneNumber()) != null) {
            throw new FieldAlreadyInUseException("phone number", reqDTO.phoneNumber());
        }

        userRepository.save(createUser(reqDTO));
    }

    private User createUser(CreateUserReqDTO reqDTO) {
        User user = new User();
        user.setCpf(reqDTO.cpf());
        user.setName(reqDTO.name());
        user.setCity(reqDTO.city());
        user.setStreet(reqDTO.street());
        user.setPassword(passwordEncoder.encode(reqDTO.password()));
        user.setPhoneNumber(reqDTO.phoneNumber());
        user.setBirthDate(LocalDate.parse(reqDTO.birthDate()));
        user.setRole(UserRole.USER);

        return user;
    }
}
