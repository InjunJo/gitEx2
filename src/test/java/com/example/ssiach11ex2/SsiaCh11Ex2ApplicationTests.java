package com.example.ssiach11ex2;

import com.example.ssiach11ex2.domain.User;
import com.example.ssiach11ex2.proxy.AuthServerProxy;
import com.sun.jna.Pointer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SsiaCh11Ex2ApplicationTests {

    @Autowired
    private AuthServerProxy authServerProxy;

    @Autowired
    private ApplicationContext ac;

    @Value("${jwt.signing.key}")
    private String key;

    @Test
    void test() {
        int[] arr = new int[0];

        Pointer pointer = Pointer.createConstant(100);

        System.out.println(arr);

    }

    @Test
    void contextLoads() {
        SecretKey key1 = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));


        String jwt = Jwts.builder()
            .setClaims(Map.of("username","john30"))
            .signWith(key1)
            .compact();

        System.out.println(jwt);

    }

    @Test
    void testOtpServerProxy(){

        authServerProxy.sendOTP("john","3791");
    }

    @Test
    void testJWT(){
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        byte[] arr = key.getEncoded();

        for(byte b : arr){

            System.out.print(b+",");
        }
    }



}
