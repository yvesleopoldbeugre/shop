package com.asking.api_produit.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorBody {
    private int statusCode;
    private String message;
    private String errorData;
}
