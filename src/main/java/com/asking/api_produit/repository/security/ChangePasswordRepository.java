package com.asking.api_produit.repository.security;

import com.asking.api_produit.modele.security.ChangePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangePasswordRepository extends JpaRepository<ChangePassword, Long> {
    @Query(
            value = "SELECT * FROM change_password WHERE " +
                    "change_password.username = :username ORDER BY id DESC LIMIT 1;",
            nativeQuery = true
    )
    ChangePassword findByUsernameLast(@Param("username") String username);
}
