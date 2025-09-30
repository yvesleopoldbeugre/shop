package com.asking.api_produit.dto.security;

import com.asking.api_produit.modele.security.Profile;
import com.asking.api_produit.modele.security.Rights;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpdateGroup {
    private String name;
    private String description;
    private List<Long> rightIds;

    public Profile toUpdateGroup(Profile group, List<Rights> rights) {
        if (this.name != null)
            group.setName(this.name);
        if (this.description != null)
            group.setDescription(this.description);
        if (this.rightIds != null)
            group.setRights(rights);
        return group;
    }
}
