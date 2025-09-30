package com.asking.api_produit.dto.auhtentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseLogin {
    private String access_token;
    private long expires_in;
    private long refresh_expires_in;
    private String refresh_token;
    private String token_type;
    private String session_state;
    private String scope;
}
