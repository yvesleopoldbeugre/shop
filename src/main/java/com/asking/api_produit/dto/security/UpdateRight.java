package com.asking.api_produit.dto.security;

import com.asking.api_produit.modele.security.Rights;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRight {
    private String name;
    private String description;

    public Rights toUpdateRight(Rights right) {
        if (this.name != null)
            right.setName(this.name);
        if (this.description != null)
            right.setDescription(this.description);
        return right;
    }
}
