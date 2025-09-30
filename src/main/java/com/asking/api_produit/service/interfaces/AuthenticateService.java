package com.asking.api_produit.service.interfaces;

import com.asking.api_produit.dto.auhtentication.Login;

public interface AuthenticateService {
    Object login(Login login);

    Object logout(String authorization);

    Object validateOldPassword(String oldPass, String username);

    Object changePassword(String newPass, String username);

    void resetPassword(String token, String newPass);

    Object refreshToken(String refreshToken);
}
