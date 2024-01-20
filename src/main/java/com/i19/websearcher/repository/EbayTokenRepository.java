package com.i19.websearcher.repository;

import com.i19.websearcher.model.token.EbayTokenData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EbayTokenRepository extends JpaRepository<EbayTokenData, Long> {
    EbayTokenData findTopByOrderByIdDesc();
}
