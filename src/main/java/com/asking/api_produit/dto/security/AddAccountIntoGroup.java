package com.asking.api_produit.dto.security;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddAccountIntoGroup {
    @NotNull(message = "userIds est requis")
    private List<Long> userIds;

    @NotNull(message = "groupId est requis")
    private Long groupId;
}
