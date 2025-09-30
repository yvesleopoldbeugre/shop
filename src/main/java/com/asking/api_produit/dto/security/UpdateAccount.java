package com.asking.api_produit.dto.security;

import com.asking.api_produit.modele.security.Account;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAccount {
    private String firstName;
    private String lastName;
    private String phone;

    public Account toUpdateAccount(Account account) {
        if (this.firstName != null)
            account.setFirstname(this.firstName);
        if (this.lastName != null)
            account.setLastname(this.lastName);
        return account;
    }
}
