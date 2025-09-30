package com.asking.api_produit.service.interfaces;

import com.asking.api_produit.dto.security.AccountSearch;
import com.asking.api_produit.dto.security.AddAccount;
import com.asking.api_produit.dto.security.AddAccountIntoGroup;
import com.asking.api_produit.dto.security.UpdateAccount;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AccountService {
    Object createAccount(AddAccount addAccount);

    Object updateAccount(Long accountId, UpdateAccount updateAccount);

    Object getAllAccount(int page, int limit, String sortOrder, String sortBy);

    Object searchAccount(int page, int limit, String sortOrder, String sortBy, AccountSearch accountSearch);

    Object getInfoAccount(Long accountId);

    void addUserIntoGroup(AddAccountIntoGroup addAccountIntoGroup);

    Object checkEmailExist(String email);

    Object checkPhoneExist(String phone);
}
