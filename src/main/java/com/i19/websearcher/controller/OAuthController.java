//package com.i19.websearcher.controller;
//
//import com.i19.websearcher.service.TokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class OAuthController {
//
//    private final TokenService tokenService;
//
//    @Autowired
//    public OAuthController(TokenService tokenService) {
//        this.tokenService = tokenService;
//    }
//
//    @GetMapping("/oauth/callback")
//    public String handleOAuthCallback(@RequestParam("code") String code){
//        String accessToken = tokenService.exchangeCodeForAccessToken(code);
//        // TODO: 12/11/2023
//        return "redirect:/search";
//    }
//}
