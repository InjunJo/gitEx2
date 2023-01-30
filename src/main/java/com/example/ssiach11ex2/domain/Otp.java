package com.example.ssiach11ex2.domain;

import lombok.Getter;

@Getter
public class Otp {

    private final String username;

    private final String otpCode;

    public Otp(String username, String otpCode) {
        this.username = username;
        this.otpCode = otpCode;
    }
}
