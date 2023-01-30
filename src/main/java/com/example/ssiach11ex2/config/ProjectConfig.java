package com.example.ssiach11ex2.config;

import com.example.ssiach11ex2.authentication.OtpAuthProvider;
import com.example.ssiach11ex2.authentication.UsernamePwdAuthProvider;
import com.example.ssiach11ex2.filter.InitialAuthenticationFilter;
import com.example.ssiach11ex2.proxy.AuthServerProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.example.ssiach11ex2")
public class ProjectConfig {

    @Bean
    public AuthServerProxy authServerProxy() {

        return new AuthServerProxy(restTemplate());
    }

    @Bean
    public UsernamePwdAuthProvider usernamePwdAuthProvider() {

        return new UsernamePwdAuthProvider(authServerProxy());
    }

    @Bean
    public OtpAuthProvider otpAuthProvider() {

        return new OtpAuthProvider(authServerProxy());
    }

    @Bean
    public InitialAuthenticationFilter initialAuthenticationFilter(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return new InitialAuthenticationFilter(authenticationManager(authenticationConfiguration));
    }

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {

        http.csrf().disable();

        http.addFilterAt(initialAuthenticationFilter(config), BasicAuthenticationFilter.class);
        http.authenticationProvider(usernamePwdAuthProvider())
            .authenticationProvider(otpAuthProvider());

        http.authorizeHttpRequests().anyRequest().authenticated();

        return http.build();
    }

}
