package com.example.demo.util;

import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptPasswordEncoder implements PasswordEncoder {

    private PasswordEncoder passwordEncoder;

    public BCryptPasswordEncoder(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }
}
