package com.example.checkcard.config;

import com.example.checkcard.data.entities.User;
import com.example.checkcard.data.enums.Role;
import com.example.checkcard.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
    	try {
        	// Forcer une opération pour établir l'authentification Mongo
        	userRepository.count();

        	createUserIfNotExists("admin@check-card.com", "Admin", "admin123", Role.ADMIN);
        	createUserIfNotExists("vigile@check-card.com", "Vigile", "vigile123", Role.VIGIL);

   	 } catch (Exception e) {
        	log.error("Erreur lors de l'initialisation des données : {}", e.getMessage());
    	}
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
