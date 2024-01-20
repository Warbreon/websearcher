package com.i19.websearcher.model.token;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ebay_token_data")
public class EbayTokenData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessToken;
    private String refreshToken;
    private Long expirationTime;
}
