package com.example.ssiach11ex2.authentication;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OtpAuth extends UsernamePasswordAuthenticationToken {

    public OtpAuth(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OtpAuth(Object principal, Object credentials,
        Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
