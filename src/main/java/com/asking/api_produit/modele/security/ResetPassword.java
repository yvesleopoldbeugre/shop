package com.asking.api_produit.modele.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phone;

    @Column(unique = true)
    private String token;

    @Column(unique = true)
    private String link;

    private boolean isValidateLink;

    private LocalDateTime validatedResetPasswordAt;

    private LocalDateTime createdAt;
}
