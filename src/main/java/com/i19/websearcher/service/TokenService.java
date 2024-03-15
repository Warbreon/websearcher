package com.i19.websearcher.service;

import com.i19.websearcher.config.EbayApiConfig;
import com.i19.websearcher.dto.EbayTokenResponse;
import com.i19.websearcher.model.token.EbayTokenData;
import com.i19.websearcher.repository.EbayTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class TokenService {

    private final EbayApiService ebayApiService;
    private final EbayTokenRepository ebayTokenRepository;

    public String getCurrentAccessToken() {
        EbayTokenData ebayTokenData = ebayTokenRepository.findTopByOrderByIdDesc();

        if (ebayTokenData == null || tokenIsExpired(ebayTokenData)) {
            ebayTokenData = updateEbayToken(ebayTokenData);
        }

        return ebayTokenData.getAccessToken();
    }

    private boolean tokenIsExpired(EbayTokenData ebayTokenData) {
        return Instant.now().getEpochSecond() > ebayTokenData.getExpirationTime();
    }

    private EbayTokenData updateEbayToken(EbayTokenData ebayTokenData) {
        EbayTokenResponse response;

        if (ebayTokenData == null) {
            response = ebayApiService.getNewAccessToken();
            ebayTokenData = new EbayTokenData();
        } else {
            response = ebayApiService.refreshToken(ebayTokenData.getRefreshToken());
        }
        ebayTokenData.setAccessToken(response.getAccessToken());
        ebayTokenData.setRefreshToken(response.getRefreshToken());
        ebayTokenData.setExpirationTime(Instant.now().getEpochSecond() + response.getExpiresIn());

        ebayTokenRepository.save(ebayTokenData);

        return ebayTokenData;
    }
}
