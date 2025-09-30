package com.asking.api_produit.utils.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MapAccount {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String username;
    private boolean emailIsVerified;
    private boolean phoneIsVerified;
    private String group;
    private boolean isActivated;
    private boolean isDeactivated;
    private boolean isDeleted;
    private LocalDateTime lastDisableAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime lastUpdatePasswordAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean enabled;
    private List<String> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean activated;
    private boolean deactivated;
    private boolean deleted;
}
