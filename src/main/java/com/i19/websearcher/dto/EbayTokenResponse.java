package com.i19.websearcher.dto;

import lombok.Data;

@Data
public class EbayTokenResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresIn;
    private long refreshTokenExpiresIn;

}
