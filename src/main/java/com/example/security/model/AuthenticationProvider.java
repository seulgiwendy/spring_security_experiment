package com.example.security.model;

public enum AuthenticationProvider {
    GITHUB("github"), FACEBOOK("facebook"), KAKAO("kakao"), NAVER("naver");

    private String providerId;

    private AuthenticationProvider(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderId() {
        return this.providerId;
    }
}
