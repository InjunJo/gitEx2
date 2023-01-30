package com.example.ssiach11ex2.authentication;

import com.example.ssiach11ex2.proxy.AuthServerProxy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class OtpAuthProvider implements AuthenticationProvider {

    private final AuthServerProxy authServerProxy;

    public OtpAuthProvider(AuthServerProxy authServerProxy) {
        this.authServerProxy = authServerProxy;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String username = authentication.getName();
        String otpCode = String.valueOf(authentication.getCredentials());

        boolean otpCheck = authServerProxy.sendOTP(username, otpCode);

        if (otpCheck) {
            return new OtpAuth(username, otpCode);
        } else {
            throw new BadCredentialsException("잘못된 인증 정보!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuth.class.isAssignableFrom(authentication);
    }
}
