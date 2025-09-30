package com.asking.api_produit.dto.auhtentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Login {
    private String username;
    private String password;
}
