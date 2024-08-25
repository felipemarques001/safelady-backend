package com.uast.ms_auth.entities;

import com.uast.ms_auth.dtos.user.CreateUserReqDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String cpf;
    private String firstName;
    private String lastName;
    private String city;
    private String street;
    private String password;
    private LocalDate birthDate;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(CreateUserReqDTO dto, String encodedPassword) {
        this.cpf = dto.cpf();
        this.firstName = dto.firstName();
        this.lastName = dto.lastName();
        this.city = dto.city();
        this.street = dto.street();
        this.password = encodedPassword;
        this.birthDate = LocalDate.parse(dto.birthDate());
        this.phoneNumber = dto.phoneNumber();
        this.role = UserRole.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role == UserRole.ADMIN
                ? List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"))
                : List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return cpf;
    }
}
