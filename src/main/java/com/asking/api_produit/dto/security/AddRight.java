package com.asking.api_produit.dto.security;

import com.asking.api_produit.modele.security.Rights;
import com.asking.api_produit.service.BaseFunction;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRight {
    @NotNull(message = "name est requis")
    private String name;

    @NotNull(message = "description est requis")
    private String description;

    public Rights toRight () {
        return Rights.builder().name(this.name).slug(BaseFunction.toSlug(this.name)).description(this.description).build();
    }
}
