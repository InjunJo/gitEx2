package com.example.ssiach11ex2.authentication;

import com.example.ssiach11ex2.proxy.AuthServerProxy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class UsernamePwdAuthProvider implements AuthenticationProvider {

    private final AuthServerProxy authServerProxy;

    public UsernamePwdAuthProvider(AuthServerProxy authServerProxy) {
        this.authServerProxy = authServerProxy;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {

        String username = authentication.getName();
        String pwd = String.valueOf(authentication.getCredentials());

        authServerProxy.sendAuth(username, pwd);

        return new UsernamePwdAuth(username,pwd);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePwdAuth.class.isAssignableFrom(authentication);
    }
}
