package com.rngay.feign.oauth.dto;

public class RsaDTO {

    private String priveKey;

    private String publicKey;

    public String getPriveKey() {
        return priveKey;
    }

    public void setPriveKey(String priveKey) {
        this.priveKey = priveKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
