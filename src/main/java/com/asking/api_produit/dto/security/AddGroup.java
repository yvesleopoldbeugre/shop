package com.asking.api_produit.dto.security;

import com.asking.api_produit.modele.security.Profile;
import com.asking.api_produit.modele.security.Rights;
import com.asking.api_produit.service.BaseFunction;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddGroup {
    @NotNull(message = "name est requis")
    private String name;

    @NotNull(message = "description est requis")
    private String description;

    private List<Long> rightIds;

    public Profile toGroup(List<Rights> rights) {
        return Profile.builder().name(this.name).slug(BaseFunction.toSlug(this.name)).description(this.description).rights(rights).build();
    }
}
