package com.example.checkcard.config;

import com.example.checkcard.data.entities.User;
import com.example.checkcard.data.enums.Role;
import com.example.checkcard.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createUserIfNotExists("admin@check-card.com", "Admin", "admin123", Role.ADMIN);
        createUserIfNotExists("vigile@check-card.com", "Vigile", "vigile123", Role.VIGIL);
    }

    private void createUserIfNotExists(String email, String name, String password, Role role) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = User.builder()
                    .email(email)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build();
            userRepository.save(user);
            log.info("Utilisateur {} créé avec le rôle {}", email, role);
        } else {
            log.info("Utilisateur {} existe déjà", email);
        }
    }
}
