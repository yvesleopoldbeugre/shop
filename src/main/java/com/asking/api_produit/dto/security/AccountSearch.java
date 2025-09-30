package com.asking.api_produit.dto.security;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountSearch {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String phone;

    private Long groupId;

    private Boolean isActivated;
    private Boolean isDeactivated;
    private Boolean isDeleted;


    private LocalDateTime lastDisableAfter;
    private LocalDateTime lastDisableBefore;

    private LocalDateTime lastLoginAfter;
    private LocalDateTime lastLoginBefore;

    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;

    private LocalDateTime updatedAfter;
    private LocalDateTime updatedBefore;
}
