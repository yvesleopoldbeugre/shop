package com.asking.api_produit.repository.security;

import com.asking.api_produit.modele.security.Rights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RightsRepository extends JpaRepository<Rights, Long> {
}
