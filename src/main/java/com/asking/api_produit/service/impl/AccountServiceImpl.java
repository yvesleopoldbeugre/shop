package com.asking.api_produit.service.impl;

import com.asking.api_produit.dto.security.AccountSearch;
import com.asking.api_produit.dto.security.AddAccount;
import com.asking.api_produit.dto.security.AddAccountIntoGroup;
import com.asking.api_produit.dto.security.UpdateAccount;
import com.asking.api_produit.exceptions.BadRequestException;
import com.asking.api_produit.modele.security.Account;
import com.asking.api_produit.modele.security.Profile;
import com.asking.api_produit.repository.security.AccountRepository;
import com.asking.api_produit.service.BaseFunction;
import com.asking.api_produit.service.interfaces.AccountService;
import com.asking.api_produit.utils.Pagination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final BaseFunction baseFunction;
    private final QueryAccountFactorImpl queryAccountFactor;
    private final AccountRepository accountRepository;

    @Override
    public Object createAccount(AddAccount addAccount) {
        if (!BaseFunction.checkPhoneNumber(addAccount.getPhone())) {
            log.error("This phone: {} bad format", addAccount.getPhone());
            throw new BadRequestException("Cet numéro n'est pas correcte");
        }

        if (baseFunction.checkPhoneNumberExist(addAccount.getPhone())) {
            log.error("This phone: {} already user", addAccount.getPhone());
            throw new BadRequestException("Cet numéro est déjà utilisé");
        }
        return baseFunction.mapAccount(accountRepository.save(addAccount.toAccount(BaseFunction.toUsername(addAccount.getPhone()))));
    }

    @Override
    public Object updateAccount(Long accountId, UpdateAccount updateAccount) {
        Account account = baseFunction.checkAccountByAccountId(accountId);
        return baseFunction.mapAccount(accountRepository.save(updateAccount.toUpdateAccount(account)));
    }

    @Override
    public Object getAllAccount(int page, int limit, String sortOrder, String sortBy) {
        Sort.Direction sortDirection = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page <= 1 ? 0 : page - 1, limit, Sort.by(sortDirection, sortBy));
        Page<Account> obj = accountRepository.findAll(pageable);
        return Pagination.builder()
                .limit(limit)
                .page(page)
                .previousPage(page == 1 ? 1 : page - 1)
                .nextPage(page == obj.getTotalPages() ? page : page + 1)
                .totalElements(obj.getTotalElements())
                .totalPages(obj.getTotalPages())
                .data(obj.getContent().stream().map(baseFunction::mapAccount).toList())
                .build();    }

    @Override
    public Object searchAccount(int page, int limit, String sortOrder, String sortBy, AccountSearch accountSearch) {
        return queryAccountFactor.searchUser(page,limit,sortOrder,sortBy,accountSearch);
    }

    @Override
    public Object getInfoAccount(Long accountId) {
        return baseFunction.mapAccount(baseFunction.checkAccount(accountId));
    }

    @Override
    public void addUserIntoGroup(AddAccountIntoGroup addAccountIntoGroup) {
        Profile profile = baseFunction.checkGroups(addAccountIntoGroup.getGroupId());
        addAccountIntoGroup.getUserIds().forEach(userId -> {
            Account account = baseFunction.checkAccount(userId);
            account.setProfile(profile);
            accountRepository.save(account);
        });
    }

    @Override
    public Object checkEmailExist(String email) {
        return baseFunction.checkExistEmail(email);
    }

    @Override
    public Object checkPhoneExist(String phone) {
        return baseFunction.checkPhoneNumberExist(phone);
    }

}
