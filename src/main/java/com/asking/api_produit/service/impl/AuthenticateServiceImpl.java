package com.asking.api_produit.service.impl;

import com.asking.api_produit.config.JwtTokenProvider;
import com.asking.api_produit.dto.auhtentication.Login;
import com.asking.api_produit.dto.auhtentication.ResponseLogin;
import com.asking.api_produit.exceptions.BadCredentialsExceptions;
import com.asking.api_produit.modele.security.Account;
import com.asking.api_produit.modele.security.ChangePassword;
import com.asking.api_produit.modele.security.ResetPassword;
import com.asking.api_produit.repository.security.AccountRepository;
import com.asking.api_produit.repository.security.ChangePasswordRepository;
import com.asking.api_produit.repository.security.UpdatePasswordRepository;
import com.asking.api_produit.service.BaseFunction;
import com.asking.api_produit.service.interfaces.AuthenticateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {
    private final ChangePasswordRepository changePasswordRepository;
    private final UpdatePasswordRepository updatePasswordRepository;
    private final AuthenticationManager authenticationManager;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final BaseFunction baseFunction;

    @Override
    public Object login(Login login) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<Account> optionalAccount = accountRepository.findByUsername(login.getUsername());
            if (optionalAccount.isPresent()){
                Account account = optionalAccount.get();
                account.setLastLoginAt(LocalDateTime.now());
                accountRepository.save(account);
            }
            return ResponseLogin.builder()
                    .access_token(jwtTokenProvider.generateToken(authentication))
                    .refresh_token(jwtTokenProvider.generateRefreshToken(login.getUsername()))
                    .token_type("Bearer")
                    .expires_in(jwtTokenProvider.getEXPIRATION_TIME())
                    .refresh_expires_in(jwtTokenProvider.getREFRESH_TOKEN_EXPIRATION_MS())
                    .scope("phone")
                    .build();
        }catch (Exception e){
            log.error("Authentication failed for user: {}", login.getUsername(), e);
            throw new BadCredentialsExceptions("Login ou mot de passe incorrect");
        }
    }

    @Override
    public Object logout(String authorization) {
       return null;
    }

    @Override
    public Object changePassword(String newPass, String username) {
        Account account = baseFunction.checkAccountByUsername(username);
        ChangePassword changePassword = changePasswordRepository.findByUsernameLast(username);
        if (changePassword == null){
            log.error("This password doesn't update, l'ancien mot de passe n'a pas été validé");
            throw new ServiceException("Impossible de mettre à jour le mot de passe, veillez valider votre ancien mot de passe");
        }
        if (!changePassword.isOldPasswordValidate()){
            log.error("This password doesn't update, la validation de l'ancien mot de passe a échoué");
            throw new ServiceException("Impossible de mettre à jour le mot de passe, la validation de votre ancien mot de passe a échoué");
        }
        account.setPassword(BCrypt.hashpw(newPass, BCrypt.gensalt()));
        account.setUpdatedAt(LocalDateTime.now());
        account.setLastUpdatePasswordAt(LocalDateTime.now());
        accountRepository.save(account);
        return null;
    }

    @Override
    public Object validateOldPassword(String oldPass, String username) {
        Account account = baseFunction.checkAccountByUsername(username);
        ChangePassword changePassword = changePasswordRepository.save(
                ChangePassword.builder()
                        .username(username)
                        .isOldPasswordValidate(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        if (passwordEncoder.matches(oldPass, account.getPassword())){
            changePassword.setOldPasswordValidate(true);
            changePassword.setValidateUpdatedPasswordAt(LocalDateTime.now());
            changePasswordRepository.save(changePassword);
            return true;
        }
        return false;
    }

    @Override
    public void resetPassword(String token, String newPass) {
        Optional<ResetPassword> optionalUpdatePassword = updatePasswordRepository.findByToken(token);
        if (optionalUpdatePassword.isPresent()){
            ResetPassword updatePassword = optionalUpdatePassword.get();
            if (updatePassword.isValidateLink()){
                log.error("Impossible de rénitialiser le mot de passe, token: {}", token);
                throw new ServiceException("Impossible de rénitialiser le mot de passe, le lien de rénitialisation n'a pas été validé");
            }
            Account account = baseFunction.checkAccountByEmail(updatePassword.getEmail());
            account.setPassword(BCrypt.hashpw(newPass, BCrypt.gensalt()));
            account.setUpdatedAt(LocalDateTime.now());
            account.setLastUpdatePasswordAt(LocalDateTime.now());
            accountRepository.save(account);

            updatePassword.setValidatedResetPasswordAt(LocalDateTime.now());
            updatePasswordRepository.save(updatePassword);
        }else {
            log.error("Impossible de rénitialiser le mot de passe");
            throw new ServiceException("Impossible de rénitialiser le mot de passe, veillez effectuer une demande de rénitialisation");
        }
    }

    @Override
    public Object refreshToken(String refreshToken) {
        return jwtTokenProvider.generateRefreshToken(jwtTokenProvider.getUsernameFromToken(refreshToken));
    }
}
