package com.i19.websearcher.scheduler;

import com.i19.websearcher.dto.EbayTokenResponse;
import com.i19.websearcher.model.token.EbayTokenData;
import com.i19.websearcher.repository.EbayTokenRepository;
import com.i19.websearcher.service.EbayApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenRefreshScheduler {

    private final EbayApiService ebayApiService;
    private final EbayTokenRepository ebayTokenRepository;

    public TokenRefreshScheduler(EbayApiService ebayApiService, EbayTokenRepository ebayTokenRepository) {
        this.ebayApiService = ebayApiService;
        this.ebayTokenRepository = ebayTokenRepository;
    }

    @Scheduled(fixedRate = 1000 * 60)
    public void refreshEbayToken() {
        EbayTokenData currentTokenData = ebayTokenRepository.findById(1L).orElse(null);

        if (currentTokenData == null) {
            EbayTokenResponse tokenResponse = ebayApiService.getAccessTokenUsingCode("v^1.1#i^1#f^0#p^3#r^0#I^3#t^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");
            saveNewTokenData(tokenResponse);
        } else if (isTokenExpired(currentTokenData)) {
            EbayTokenResponse newTokenResponse = ebayApiService.refreshToken(currentTokenData.getRefreshToken());
            saveNewTokenData(newTokenResponse);
        } else if (isTokenExpiringSoon(currentTokenData)) {
            EbayTokenResponse updatedTokenResponse = ebayApiService.refreshToken(currentTokenData.getRefreshToken());
            saveNewTokenData(updatedTokenResponse);
        }
    }

    private boolean isTokenExpired(EbayTokenData ebayTokenData) {
        return Instant.now().getEpochSecond() > ebayTokenData.getExpirationTime();
    }

    private boolean isTokenExpiringSoon(EbayTokenData ebayTokenData) {
        return Instant.now().getEpochSecond() > (ebayTokenData.getExpirationTime() - 3600);
    }

    private void saveNewTokenData(EbayTokenResponse ebayTokenResponse) {
        EbayTokenData newTokenData = new EbayTokenData();

        newTokenData.setAccessToken(ebayTokenResponse.getAccessToken());
        newTokenData.setRefreshToken(ebayTokenResponse.getRefreshToken());
        newTokenData.setExpirationTime(Instant.now().getEpochSecond() + ebayTokenResponse.getExpiresIn());

        ebayTokenRepository.save(newTokenData);
    }
}
