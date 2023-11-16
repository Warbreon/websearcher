package com.i19.websearcher.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EbayService {

    @Value("${ebay.api.sandbox.app-id}")
    private String appId;
    @Value("${ebay.api.sandbox.dev-id}")
    private String devId;
    @Value("${ebay.api.sandbox.cert-id}")
    private String certId;
}
