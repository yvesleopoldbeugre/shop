package com.asking.api_produit.controller.impl.security;

import com.asking.api_produit.controller.interfaces.security.AccountController;
import com.asking.api_produit.dto.security.AccountSearch;
import com.asking.api_produit.dto.security.AddAccount;
import com.asking.api_produit.dto.security.AddAccountIntoGroup;
import com.asking.api_produit.dto.security.UpdateAccount;
import com.asking.api_produit.service.impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/gestion-produit/security/account")
@RequiredArgsConstructor
@Tag(name = "Comptes", description = "Gestion des comptes")
public class AccountImpl implements AccountController {

    private final AccountServiceImpl accountServiceImpl;

    @Override
    public ResponseEntity<?> addAccount(AddAccount addAccount) {
        log.info("security: add-account, body: {}", addAccount);
        return ResponseEntity.status(201).body(accountServiceImpl.createAccount(addAccount));
    }

    @Override
    public ResponseEntity<?> updateAccount(Long accountId, UpdateAccount updateAccount) {
        log.info("security: update-account accountId: {}, body: {}",accountId, updateAccount);
        return ResponseEntity.status(200).body(accountServiceImpl.updateAccount(accountId,updateAccount));
    }

    @Override
    public ResponseEntity<?> getAllAccount(int page, int limit, String sortOrder, String sortBy) {
        log.info("security: get-all-account");
        return ResponseEntity.status(200).body(accountServiceImpl.getAllAccount(page,limit,sortOrder,sortBy));
    }

    @Override
    public ResponseEntity<?> searchAccount(int page, int limit, String sortOrder, String sortBy, AccountSearch accountSearch) {
        log.info("search account");
        return ResponseEntity.status(200).body(accountServiceImpl.searchAccount(page,limit,sortOrder,sortBy,accountSearch));
    }

    @Override
    public ResponseEntity<?> getInfoAccount(Long accountId) {
        log.info("security: get-info-account, accountId: {}", accountId);
        return ResponseEntity.status(200).body(accountServiceImpl.getInfoAccount(accountId));
    }

    @Override
    public ResponseEntity<?> addUserIntoGroup(AddAccountIntoGroup addAccountIntoGroup) {
        log.info("security: add-user-into-group, {}", addAccountIntoGroup);
        accountServiceImpl.addUserIntoGroup(addAccountIntoGroup);
        return ResponseEntity.status(200).body(null);
    }

    @Override
    public ResponseEntity<?> checkEmailExist(String email) {
        log.info("security: check-email-exist, {}", email);
        return ResponseEntity.status(200).body(accountServiceImpl.checkEmailExist(email));
    }

    @Override
    public ResponseEntity<?> checkPhoneExist(String phone) {
        log.info("security: check-phone-exist, {}", phone);
        return ResponseEntity.status(200).body(accountServiceImpl.checkPhoneExist(phone));
    }
}
