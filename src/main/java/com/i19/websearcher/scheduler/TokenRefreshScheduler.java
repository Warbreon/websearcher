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
