package com.asking.api_produit.service;


import com.asking.api_produit.exceptions.NotFoundException;
import com.asking.api_produit.modele.security.Account;
import com.asking.api_produit.modele.security.Profile;
import com.asking.api_produit.repository.security.AccountRepository;
import com.asking.api_produit.repository.security.GroupsRepository;
import com.asking.api_produit.utils.response.MapAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseFunction {
    private final AccountRepository accountRepository;
    private final GroupsRepository groupsRepository;

    private static final AtomicInteger counter = new AtomicInteger(1);
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public boolean checkEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    public boolean checkExistEmail(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        return optionalAccount.isPresent();
    }

    public boolean checkPhoneNumberExist(String phoneNumber){
        Optional<Account> optionalAccount = accountRepository.findByPhone(phoneNumber);
        return optionalAccount.isPresent();
    }

    public static boolean checkPhoneNumber(String phoneNumber){
        if (phoneNumber == null || phoneNumber.isEmpty())
            return false;
        return phoneNumber.length() == 13;
    }
    public static String toSlug(String input) {
        String slug = Normalizer.normalize(input, Normalizer.Form.NFD);
        slug = slug.replaceAll("[\\p{InCombiningDiacriticalMarks}]", ""); // Supprimer les accents
        slug = slug.toLowerCase(Locale.ENGLISH);                          // Convertir en minuscules
        slug = slug.replaceAll("[^a-z0-9\\s-]", "");                      // Supprimer caractères non-alphanumériques
        slug = slug.trim().replaceAll("\\s+", "-");                       // Remplacer espaces par tirets
        return slug;
    }

    public static String toUsername(String email) {
        String[] emailParts = email.split("@");
        return emailParts[0];
    }

    public Account checkAccount(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            log.error("Account with id {} not found", accountId);
            throw new NotFoundException("Account with id " + accountId + " not found");
        }
        return optionalAccount.get();
    }

    public Account checkAccountByUsername(String username) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isEmpty()) {
            log.error("Account with username {} not found", username);
            throw new NotFoundException("Account with username " + username + " not found");
        }
        return optionalAccount.get();
    }

    public Account checkAccountByEmail(String email) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if (optionalAccount.isEmpty()) {
            log.error("Account with email {} not found", email);
            throw new NotFoundException("Account with email " + email + " not found");
        }
        return optionalAccount.get();
    }

    public Account checkAccountByAccountId(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            log.error("Account with accountId {} not found", accountId);
            throw new NotFoundException("Account with id " + accountId + " not found");
        }
        return optionalAccount.get();
    }

    public Profile checkGroups(Long groupId){
        Optional<Profile> optionalGroups = groupsRepository.findById(groupId);
        if (optionalGroups.isEmpty()) {
            log.error("Group with id {} not found", groupId);
            throw new NotFoundException("Group with id " + groupId + " not found");
        }
        return optionalGroups.get();
    }

    public MapAccount mapAccount(Account account){
        return MapAccount.builder()
                .id(account.getId())
                .firstname(account.getFirstname())
                .lastname(account.getLastname())
                .email(account.getEmail())
                .phone(account.getPhone())
                .username(account.getUsername())
                .emailIsVerified(account.isEmailIsVerified())
                .phoneIsVerified(account.isPhoneIsVerified())
                .group(account.getProfile() != null ? account.getProfile().getName():null)
                .lastDisableAt(account.getLastDisableAt())
                .lastLoginAt(account.getLastLoginAt())
                .lastUpdatePasswordAt(account.getLastUpdatePasswordAt())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .enabled(account.isEnabled())
                .authorities(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .accountNonExpired(account.isAccountNonExpired())
                .accountNonLocked(account.isAccountNonLocked())
                .credentialsNonExpired(account.isCredentialsNonExpired())
                .activated(account.isActivated())
                .deactivated(account.isDeactivated())
                .deleted(account.isDeleted())
                .build();
    }
}
