package com.i19.websearcher.service;

import com.i19.websearcher.dto.EbayTokenResponse;
import com.i19.websearcher.model.token.EbayTokenData;
import com.i19.websearcher.repository.EbayTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final EbayApiService ebayApiService;
    private final EbayTokenRepository ebayTokenRepository;

    public TokenService(EbayApiService ebayApiService, EbayTokenRepository ebayTokenRepository) {
        this.ebayApiService = ebayApiService;
        this.ebayTokenRepository = ebayTokenRepository;
    }

    public String getCurrentAccessToken() {
        EbayTokenData ebayTokenData = ebayTokenRepository.findTopByOrderByIdDesc();
        return ebayTokenData.getAccessToken();
    }
}
