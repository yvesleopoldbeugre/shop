package com.asking.api_produit.controller.interfaces.security;

import com.asking.api_produit.dto.auhtentication.Login;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthenticationController {
    @PostMapping("/login")
    @Operation(summary = "Connexion")
    ResponseEntity<?> authenticate(@RequestBody Login login);

    @PostMapping("/logout")
    @Operation(summary = "Deconnexion")
    ResponseEntity<?> logout(HttpServletRequest request);

    @PutMapping(value = "/refresh-token")
    @Operation(summary = "Générer un nouvel acces token")
    ResponseEntity<?> refreshToken(@RequestParam String refreshToken);

    @PutMapping("/change-password")
    @Operation(summary = "Modifier le mot de passe")
    ResponseEntity<?> changePassword(@RequestParam String newPass, HttpServletRequest servletRequest);

    @PutMapping("/change-password/validate-old-password")
    @Operation(summary = "Valider l'ancien mot de passe")
    ResponseEntity<?> validateOldPassword(@RequestParam String oldPass, HttpServletRequest servletRequest);

    @PutMapping("/reset-password")
    @Operation(summary = "Renitialiser le mot de passe")
    ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPass);
}
