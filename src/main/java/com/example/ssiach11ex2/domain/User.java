package com.example.ssiach11ex2.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
public class User {

    public static void main(String[] args) {

    }

    private String username;

    private String password;

    private String code;

}
