package com.suyash.carrental.config;

import com.suyash.carrental.model.User;
import com.suyash.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@carrental.com")) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@carrental.com")
                    .password(passwordEncoder.encode("admin123"))
                    .phone("0000000000")
                    .roles(Set.of("ADMIN", "USER"))
                    .build();

            userRepository.save(admin);
            log.info("✅ Default admin created — email: admin@carrental.com | password: admin123");
        } else {
            log.info("ℹ️ Admin already exists, skipping seed.");
        }
    }
}