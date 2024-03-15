package com.i19.websearcher.scheduler;

import com.i19.websearcher.dto.EbayTokenResponse;
import com.i19.websearcher.model.token.EbayTokenData;
import com.i19.websearcher.repository.EbayTokenRepository;
import com.i19.websearcher.service.EbayApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
@AllArgsConstructor
public class TokenRefreshScheduler {

    private final EbayApiService ebayApiService;
    private final EbayTokenRepository ebayTokenRepository;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void refreshEbayToken() {
        EbayTokenData currentTokenData = ebayTokenRepository.findById(1L).orElse(null);

        if (currentTokenData == null) {
            EbayTokenResponse tokenResponse = ebayApiService.getNewAccessToken();
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
        if (ebayTokenResponse.getAccessToken() == null || ebayTokenResponse.getRefreshToken() == null) {
            log.error("Access token or refresh token is null. Token response: {}", ebayTokenResponse);
            throw new IllegalStateException("Received null token(s) from eBay API.");
        }

        EbayTokenData newTokenData = new EbayTokenData();

        newTokenData.setAccessToken(ebayTokenResponse.getAccessToken());
        newTokenData.setRefreshToken(ebayTokenResponse.getRefreshToken());
        newTokenData.setExpirationTime(Instant.now().getEpochSecond() + ebayTokenResponse.getExpiresIn());

        ebayTokenRepository.save(newTokenData);
        log.info("New token data saved. Access token: {}", newTokenData.getAccessToken());
    }
}
