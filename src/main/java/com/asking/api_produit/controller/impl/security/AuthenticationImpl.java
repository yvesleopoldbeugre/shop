package com.asking.api_produit.controller.impl.security;

import com.asking.api_produit.config.JwtTokenProvider;
import com.asking.api_produit.controller.interfaces.security.AuthenticationController;
import com.asking.api_produit.dto.auhtentication.Login;
import com.asking.api_produit.service.impl.AuthenticateServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/gestion-produit/security/authentication")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Gestion de l'authentification")
public class AuthenticationImpl implements AuthenticationController {
    private final AuthenticateServiceImpl authenticateServiceImpl;
    private final JwtTokenProvider tokenProvider;

    @Override
    public ResponseEntity<?> authenticate(Login login) {
        log.info("Authenticating user {}", login.getUsername());
        return ResponseEntity.status(200).body(authenticateServiceImpl.login(login));
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        log.info("Logging out");
        return ResponseEntity.status(200).body(authenticateServiceImpl.logout(request.getHeader("Authorization").split(" ")[1]));
    }

    @Override
    public ResponseEntity<?> changePassword(String newPass, HttpServletRequest servletRequest) {
        String username = tokenProvider.getUsernameFromToken(servletRequest.getHeader("Authorization").split(" ")[1]);
        log.info("Changing password for user: {}", username);
        return ResponseEntity.status(200).body(authenticateServiceImpl.changePassword(newPass,username));
    }

    @Override
    public ResponseEntity<?> validateOldPassword(String oldPass, HttpServletRequest servletRequest) {
        String username = tokenProvider.getUsernameFromToken(servletRequest.getHeader("Authorization").split(" ")[1]);
        log.info("Validate old password for user: {}", username);
        return ResponseEntity.status(200).body(authenticateServiceImpl.validateOldPassword(oldPass, username));
    }

    @Override
    public ResponseEntity<?> resetPassword(String token, String newPass) {
        log.info("Reset password for user, token {}", token);
        authenticateServiceImpl.resetPassword(token,newPass);
        return ResponseEntity.status(200).body(null);
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        log.info("Generate a new access token");
        return ResponseEntity.status(200).body(authenticateServiceImpl.refreshToken(refreshToken));
    }
}
