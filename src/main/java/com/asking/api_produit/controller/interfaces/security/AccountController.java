package com.asking.api_produit.controller.interfaces.security;

import com.asking.api_produit.dto.security.AccountSearch;
import com.asking.api_produit.dto.security.AddAccount;
import com.asking.api_produit.dto.security.AddAccountIntoGroup;
import com.asking.api_produit.dto.security.UpdateAccount;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AccountController {
    @PostMapping(value = "/add-account", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Créer un nouveau compte")
    ResponseEntity<?> addAccount(@Validated @RequestBody AddAccount addAccount);

    @PutMapping(value = "/update-account/{accountId}")
    @Operation(summary = "Mettre à jour les information d'un compte")
    ResponseEntity<?> updateAccount(@PathVariable Long accountId, @Validated @RequestBody UpdateAccount updateAccount);

    @GetMapping("/all-account")
    @Operation(summary = "Récupérer tous les comptes")
    ResponseEntity<?> getAllAccount(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "100") int size,
            @RequestParam(value = "sortOrder", defaultValue = "Desc") String sortOrder,
            @RequestParam(value = "orderBy", defaultValue = "id") String sortBy
    );

    @PostMapping("/search-account")
    @Operation(summary = "Récupérer tous les comptes")
    ResponseEntity<?> searchAccount(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "100") int size,
            @RequestParam(value = "sortOrder", defaultValue = "Desc") String sortOrder,
            @RequestParam(value = "orderBy", defaultValue = "id") String sortBy,
            @RequestBody AccountSearch accountSearch
            );

    @GetMapping("/info-account/{accountId}")
    @Operation(summary = "Récupérer les informations d'un comptes")
    ResponseEntity<?> getInfoAccount(@PathVariable Long accountId);

    @PutMapping("/add-user-into-group")
    @Operation(summary = "Ajouter des utilisateurs à un groupe")
    ResponseEntity<?> addUserIntoGroup(@RequestBody AddAccountIntoGroup addAccountIntoGroup);

    @PutMapping(value = "/check-email-exist")
    @Operation(summary = "Vérifier si l'email exist")
    ResponseEntity<?> checkEmailExist(@RequestParam String email);

    @PutMapping(value = "/check-phone-exist")
    @Operation(summary = "Vérifier si le numéro de téléphone exist")
    ResponseEntity<?> checkPhoneExist(@RequestParam String phone);
}
