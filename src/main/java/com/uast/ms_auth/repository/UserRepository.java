package com.uast.ms_auth.repository;

import com.uast.ms_auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByCpf(String cpf);
    User findByPhoneNumber(String phoneNumber);
}
