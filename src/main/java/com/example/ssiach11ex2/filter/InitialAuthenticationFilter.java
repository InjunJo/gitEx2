package com.example.ssiach11ex2.filter;

import com.example.ssiach11ex2.authentication.OtpAuth;
import com.example.ssiach11ex2.authentication.UsernamePwdAuth;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager manager;

    @Value("${jwt.signing.key}")
    private String signingKey;

    public InitialAuthenticationFilter(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {


        String username = request.getHeader("username");
        String pwd = request.getHeader("password");
        String otpCode = request.getHeader("otpCode");


        if(otpCode == null){
            log.info("filter...........otp code null");
            log.info("username : "+username);
            log.info("pwd : "+pwd);
            Authentication auth = new UsernamePwdAuth(username,pwd);
            log.info("manager : "+manager);
            manager.authenticate(auth);

        }else {

            Authentication auth = new OtpAuth(username, otpCode);

            auth = manager.authenticate(auth);

            SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                .setClaims(Map.of("username", username))
                .signWith(key)
                .compact();

            response.setHeader("Authorization", jwt);

        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
