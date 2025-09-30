package com.asking.api_produit.service.interfaces;


import com.asking.api_produit.dto.security.AddRight;
import com.asking.api_produit.dto.security.UpdateRight;

public interface RightService {
    Object addRight(AddRight addRight);
    Object updateRight(UpdateRight updateRight, Long rightId);
    Object getAllRights();
    Object getRightById(Long id);
    void deleteRight(Long id);
}
