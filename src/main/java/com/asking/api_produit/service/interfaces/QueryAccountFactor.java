package com.asking.api_produit.service.interfaces;


import com.asking.api_produit.dto.security.AccountSearch;

public interface QueryAccountFactor {
    Object searchUser(int page, int limit, String sortOrder, String sortBy, AccountSearch criteria);
}
