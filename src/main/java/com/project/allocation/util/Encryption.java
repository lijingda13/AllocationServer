package com.project.allocation.util;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Encryption {

    /**
     * Use the BCrypt algorithm for hashing the password
     * Since many validators in Spring Security need to use the encryption of {@link PasswordEncoder},
     * thus we need to add the @Bean annotation to publish it
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Encode the password using the default encryption algorithm
     */
    public String encode(CharSequence rawPassword) {
        return passwordEncoder().encode(Optional.ofNullable(rawPassword).orElse(""));
    }
}
