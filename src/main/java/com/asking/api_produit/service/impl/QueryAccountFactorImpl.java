package com.asking.api_produit.service.impl;

import com.asking.api_produit.dto.security.AccountSearch;
import com.asking.api_produit.modele.security.Account;
import com.asking.api_produit.modele.security.QAccount;
import com.asking.api_produit.service.interfaces.QueryAccountFactor;
import com.asking.api_produit.utils.Pagination;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryAccountFactorImpl implements QueryAccountFactor {
    private final JPAQueryFactory queryFactory;

    @Override
    public Object searchUser(int page, int limit, String sortOrder, String sortBy, AccountSearch criteria) {
        QAccount qUser = QAccount.account;
        BooleanBuilder builder = new BooleanBuilder();

        if (criteria.getFirstname() != null && !criteria.getFirstname().isBlank()) {
            builder.and(qUser.firstname.containsIgnoreCase(criteria.getFirstname()));
        }
        if (criteria.getLastname() != null && !criteria.getLastname().isBlank()) {
            builder.and(qUser.lastname.containsIgnoreCase(criteria.getLastname()));
        }
        if (criteria.getUsername() != null && !criteria.getUsername().isBlank()) {
            builder.and(qUser.username.containsIgnoreCase(criteria.getUsername()));
        }
        if (criteria.getEmail() != null && !criteria.getEmail().isBlank()) {
            builder.and(qUser.email.containsIgnoreCase(criteria.getEmail()));
        }
        if (criteria.getPhone() != null && !criteria.getPhone().isBlank()) {
            builder.and(qUser.phone.containsIgnoreCase(criteria.getPhone()));
        }


        if (criteria.getIsActivated() != null) {
            builder.and(qUser.isActivated.eq(criteria.getIsActivated()));
        }
        if (criteria.getIsDeactivated() != null) {
            builder.and(qUser.isDeactivated.eq(criteria.getIsDeactivated()));
        }
        if (criteria.getIsDeleted() != null) {
            builder.and(qUser.isDeleted.eq(criteria.getIsDeleted()));
        }

        if (criteria.getLastDisableAfter() != null) {
            builder.and(qUser.lastDisableAt.goe(criteria.getLastDisableAfter()));
        }
        if (criteria.getLastLoginBefore() != null) {
            builder.and(qUser.lastDisableAt.loe(criteria.getLastLoginBefore()));
        }

        if (criteria.getCreatedAfter() != null) {
            builder.and(qUser.createdAt.goe(criteria.getCreatedAfter()));
        }
        if (criteria.getCreatedBefore() != null) {
            builder.and(qUser.createdAt.loe(criteria.getCreatedBefore()));
        }

        if (criteria.getLastLoginAfter() != null) {
            builder.and(qUser.lastLoginAt.goe(criteria.getLastLoginAfter()));
        }
        if (criteria.getLastLoginBefore() != null) {
            builder.and(qUser.lastLoginAt.loe(criteria.getLastLoginBefore()));
        }

        if (criteria.getGroupId() != null) {
            builder.and(qUser.profile.id.eq(criteria.getGroupId()));
        }

        int pageIndex = Math.max(0, page - 1);
        long offset = (long) pageIndex * limit;

        PathBuilder<Account> entityPath = new PathBuilder<>(Account.class, "account");
        OrderSpecifier<?> orderSpecifier = sortOrder.equalsIgnoreCase("desc")
                ? entityPath.getString(sortBy).desc()
                : entityPath.getString(sortBy).asc();

        long total = queryFactory.selectFrom(qUser)
                .where(builder)
                .stream().count();

        int totalPages = (int) Math.ceil((double) total / limit);
        return Pagination.builder()
                .limit(limit)
                .page(page)
                .previousPage(page == 1 ? 1 : page - 1)
                .nextPage(page == totalPages ? page : page + 1)
                .totalElements(total)
                .totalPages(totalPages)
                .data(
                        queryFactory
                                .selectFrom(qUser)
                                .leftJoin(qUser.profile).fetchJoin()
                                .where(builder)
                                .orderBy(orderSpecifier)
                                .offset(offset)
                                .limit(limit)
                                .fetch()
                )
                .build();
    }
}
