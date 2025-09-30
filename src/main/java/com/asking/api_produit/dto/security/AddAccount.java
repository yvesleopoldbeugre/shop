package com.asking.api_produit.dto.security;

import com.asking.api_produit.modele.security.Account;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;

@Data
@Builder
public class AddAccount {
    @NotNull(message = "firstName est requis")
    private String firstName;

    @NotNull(message = "lastName est requis")
    private String lastName;

    @NotNull(message = "phone est requis")
    private String phone;

    @NotNull(message = "password est requis")
    private String password;

    public Account toAccount(String username) {
        return Account.builder()
                .firstname(this.firstName)
                .lastname(this.lastName)
                .phone(this.phone)
                .username(username)
                .isActivated(true)
                .isDeactivated(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .password(BCrypt.hashpw(this.password, BCrypt.gensalt()))
                .build();
    }
}
