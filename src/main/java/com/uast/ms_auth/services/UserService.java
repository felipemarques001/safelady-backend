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

        userRepository.save(new User(reqDTO, passwordEncoder.encode(reqDTO.password())));
    }
}
