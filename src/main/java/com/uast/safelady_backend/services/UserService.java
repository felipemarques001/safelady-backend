package com.uast.safelady_backend.services;

import com.uast.safelady_backend.dtos.user.CreateUserReqDTO;
import com.uast.safelady_backend.entities.User;
import com.uast.safelady_backend.exceptions.FieldAlreadyInUseException;
import com.uast.safelady_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
