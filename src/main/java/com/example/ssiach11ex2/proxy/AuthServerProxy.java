package com.example.ssiach11ex2.proxy;

import com.example.ssiach11ex2.domain.Otp;
import com.example.ssiach11ex2.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Setter
@Getter
@Log4j2
public class AuthServerProxy {

    private final RestTemplate rest;

    @Value("${auth.server.base.url}")
    private String baseUrl;

    public AuthServerProxy(RestTemplate rest) {
        this.rest = rest;
    }

    public void sendAuth(String username, String pwd) {

        String url = baseUrl + "/user/auth";

        User user = new User();

        user.setUsername(username);
        user.setPassword(pwd);

        HttpEntity<User> request = new HttpEntity<>(user);

        ResponseEntity res = rest.postForEntity(url, request, Void.class);

        log.info("res : " + res);

    }

    public boolean sendOTP(String username, String code) {

        String url = baseUrl + "/otp/check";

        Otp otp = new Otp(username, code);

        HttpEntity<Otp> otpHttpEntity = new HttpEntity<>(otp);

        ResponseEntity<Void> res = rest.postForEntity(url, otpHttpEntity, Void.class);

        log.info("Otp res : "+res);

        return res.getStatusCode().equals(HttpStatus.OK);
    }

}
